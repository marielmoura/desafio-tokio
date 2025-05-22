<template>
    <div class="container mt-4">
        <h1 class="mb-4">Transferências</h1>
        <form @submit.prevent="submitTransfer" class="mb-5">
            <div class="mb-3 d-flex align-items-center">
                <label for="sourceAccount" class="form-label me-3" style="width: 150px;">Conta origem</label>
                <input id="sourceAccount" v-model="form.sourceAccount" placeholder="Conta origem" class="form-control"
                    maxlength="10" pattern="\d{10}" title="A conta origem deve conter exatamente 10 números."
                    required />
            </div>
            <div class="mb-3 d-flex align-items-center">
                <label for="destinationAccount" class="form-label me-3" style="width: 150px;">Conta destino</label>
                <input id="destinationAccount" v-model="form.destinationAccount" placeholder="Conta destino"
                    class="form-control" maxlength="10" pattern="\d{10}"
                    title="A conta destino deve conter exatamente 10 números." required />
            </div>
            <div class="mb-3 d-flex align-items-center">
                <label for="amount" class="form-label me-3" style="width: 150px;">Valor R$</label>
                <input id="amount" v-model.number="form.amount" type="number" placeholder="Valor"
                    class="form-control" />
            </div>
            <div class="mb-3 d-flex align-items-center">
                <label for="transferDate" class="form-label me-3" style="width: 150px;">Data da transferência</label>
                <input id="transferDate" v-model="form.transferDate" type="date" class="form-control" required />
            </div>
            <button type="submit" class="btn btn-primary">Agendar</button>
        </form>

        <ul class="list-group mb-3">
            <li v-for="transfer in transfers" :key="transfer.id" class="list-group-item">
                <strong>{{ transfer.sourceAccount }}</strong> → <strong>{{ transfer.destinationAccount }}</strong><br />
                Valor: R$ {{ transfer.amount }} | Taxa: R$ {{ transfer.fee }}<br />
                Data: {{ transfer.transferDate }} | Agendado em: {{ transfer.schedulingDate }}
            </li>
        </ul>

        <div class="d-flex justify-content-between">
            <div>
                Página {{ pagination.number + 1 }} de {{ pagination.totalPages }}
            </div>
            <button class="btn btn-secondary btn-sm" :disabled="pagination.last" @click="nextPage">
                Próxima página
            </button>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { ref, onMounted } from 'vue'
    import axios from 'axios'

    interface Transfer {
        id: number
        sourceAccount: string
        destinationAccount: string
        amount: number
        fee: number
        transferDate: string
        schedulingDate: string
    }

    interface Pagination {
        totalPages: number
        totalElements: number
        last: boolean
        size: number
        number: number
        first: boolean
        numberOfElements: number
        empty: boolean
    }

    const transfers = ref < Transfer[] > ([])
    const pagination = ref < Pagination > ({
        totalPages: 0,
        totalElements: 0,
        last: false,
        size: 0,
        number: 0,
        first: false,
        numberOfElements: 0,
        empty: true
    })

    const form = ref({
        sourceAccount: '',
        destinationAccount: '',
        amount: 0,
        transferDate: ''
    })

    const loadTransfers = async (page = 0) => {
        const response = await axios.get(`http://localhost:8081/api/transfers?page=${page}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        })
        transfers.value = response.data.content
        pagination.value = {
            totalPages: response.data.totalPages,
            totalElements: response.data.totalElements,
            last: response.data.last,
            size: response.data.size,
            number: response.data.number,
            first: response.data.first,
            numberOfElements: response.data.numberOfElements,
            empty: response.data.empty
        }
    }

    const nextPage = () => {
        if (!pagination.value.last) {
            loadTransfers(pagination.value.number + 1)
        }
    }

    const submitTransfer = async () => {
        try {
            await axios.post('http://localhost:8081/api/transfers', form.value, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`
                }
            })
            form.value = {
                sourceAccount: '',
                destinationAccount: '',
                amount: 0,
                transferDate: ''
            }
            loadTransfers()
        } catch (err) {
            alert('Erro ao agendar transferência.')
            console.error(err)
        }
    }

    onMounted(() => {
        loadTransfers()
    })
</script>