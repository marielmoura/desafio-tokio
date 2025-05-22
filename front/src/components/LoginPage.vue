<template>
  <div class="container mt-5" style="max-width: 400px">
    <h2 class="mb-4">Login</h2>
    <form @submit.prevent="handleLogin">
      <div class="mb-3">
        <label for="username" class="form-label">Usuário</label>
        <input
          id="username"
          v-model="credentials.username"
          class="form-control"
          placeholder="Digite o usuário"
          required
        />
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">Senha</label>
        <input
          id="password"
          v-model="credentials.password"
          type="password"
          class="form-control"
          placeholder="Digite a senha"
          required
        />
      </div>
      <button type="submit" class="btn btn-primary w-100">Entrar</button>
    </form>

    <div v-if="errorMessage" class="alert alert-danger mt-3">{{ errorMessage }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const credentials = ref({ username: '', password: '' })
const errorMessage = ref('')
const router = useRouter()

const handleLogin = async () => {
  try {
    const { data: token } = await axios.post('http://localhost:8081/api/auth/login', credentials.value)

    localStorage.setItem('token', token)
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`

    router.push('/transfers')
  } catch (error) {
    errorMessage.value = 'Usuário ou senha inválidos.'
    console.error(error)
  }
}
</script>
