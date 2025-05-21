package com.tokiomarine.financial_transfer_scheduler.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.tokiomarine.financial_transfer_scheduler.exception.InvalidTransferException;
import com.tokiomarine.financial_transfer_scheduler.model.Transfer;


class TransferTest {

    @Test
    void shouldCalculateFeeForSameDayTransfer() {
        Transfer transfer = new Transfer("123", "456", BigDecimal.valueOf(1000), LocalDate.now());
        assertEquals(new BigDecimal("28.00"), transfer.getFee());
    }

    @Test
    void shouldCalculateFeeForTransferIn5Days() {
        Transfer transfer = new Transfer("123", "456", BigDecimal.valueOf(1000), LocalDate.now().plusDays(5));
        assertEquals(new BigDecimal("12.00"), transfer.getFee());
    }

    @Test
    void shouldCalculateFeeForTransferIn15Days() {
        Transfer transfer = new Transfer("123", "456", BigDecimal.valueOf(1000), LocalDate.now().plusDays(15));
        assertEquals(new BigDecimal("82.00"), transfer.getFee());
    }

    @Test
    void shouldCalculateFeeForTransferIn25Days() {
        Transfer transfer = new Transfer("123", "456", BigDecimal.valueOf(1000), LocalDate.now().plusDays(25));
        assertEquals(new BigDecimal("69.00"), transfer.getFee());
    }

    @Test
    void shouldCalculateFeeForTransferIn35Days() {
        Transfer transfer = new Transfer("123", "456", BigDecimal.valueOf(1000), LocalDate.now().plusDays(35));
        assertEquals(new BigDecimal("47.00"), transfer.getFee());
    }

    @Test
    void shouldCalculateFeeForTransferIn45Days() {
        Transfer transfer = new Transfer("123", "456", BigDecimal.valueOf(1000), LocalDate.now().plusDays(45));
        assertEquals(new BigDecimal("17.00"), transfer.getFee());
    }

    @Test
    void shouldThrowExceptionWhenDaysExceedLimit() {
        InvalidTransferException exception = assertThrows(InvalidTransferException.class, () ->
            new Transfer("123", "456", BigDecimal.valueOf(1000), LocalDate.now().plusDays(60))
        );
        assertEquals("Não há taxa aplicável para o número de dias informado.", exception.getMessage());
    }
}
