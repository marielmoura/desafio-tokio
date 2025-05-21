package com.tokiomarine.financial_transfer_scheduler.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.tokiomarine.financial_transfer_scheduler.dto.TransferRequestDTO;
import com.tokiomarine.financial_transfer_scheduler.exception.InvalidTransferException;
import com.tokiomarine.financial_transfer_scheduler.model.Transfer;
import com.tokiomarine.financial_transfer_scheduler.repository.TransferRepository;

public class TransferServiceTest {

    @InjectMocks
    private TransferService transferService;

    @Mock
    private TransferRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferService = new TransferService(); // manual
        transferService.repository = repository; // usar injeção manual (ou setter, se preferir)
    }

    @Test
    void testCalculateFee_sameDay() {
        BigDecimal fee = transferService.calculateFee(BigDecimal.valueOf(1000), 0);
        assertEquals(new BigDecimal("28.00"), fee);
    }

    @Test
    void testCalculateFee_between1And10Days() {
        BigDecimal fee = transferService.calculateFee(BigDecimal.valueOf(1000), 5);
        assertEquals(new BigDecimal("12.00"), fee);
    }

    @Test
    void testCalculateFee_between11And20Days() {
        BigDecimal fee = transferService.calculateFee(BigDecimal.valueOf(1000), 15);
        assertEquals(new BigDecimal("82.00"), fee);
    }

    @Test
    void testCalculateFee_between21And30Days() {
        BigDecimal fee = transferService.calculateFee(BigDecimal.valueOf(1000), 25);
        assertEquals(new BigDecimal("69.00"), fee);
    }

    @Test
    void testCalculateFee_between31And40Days() {
        BigDecimal fee = transferService.calculateFee(BigDecimal.valueOf(1000), 35);
        assertEquals(new BigDecimal("47.00"), fee);
    }

    @Test
    void testCalculateFee_between41And50Days() {
        BigDecimal fee = transferService.calculateFee(BigDecimal.valueOf(1000), 45);
        assertEquals(new BigDecimal("17.00"), fee);
    }

    @Test
    void testCalculateFee_invalidDays() {
        InvalidTransferException exception = assertThrows(InvalidTransferException.class, () -> {
            transferService.calculateFee(BigDecimal.valueOf(1000), 51);
        });
        assertNotNull(exception);
    }

    @Test
    void testSchedule_successful() {
        TransferRequestDTO dto = new TransferRequestDTO("123", "456", BigDecimal.valueOf(1000), LocalDate.now());
        when(repository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Transfer result = transferService.schedule(dto);

        assertNotNull(result);
        assertEquals(dto.getAmount(), result.getAmount());
        assertEquals(dto.getSourceAccount(), result.getSourceAccount());
        assertEquals(dto.getDestinationAccount(), result.getDestinationAccount());
    }

    @Test
    void testListAll_empty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(transferService.listAll().isEmpty());
    }
}
