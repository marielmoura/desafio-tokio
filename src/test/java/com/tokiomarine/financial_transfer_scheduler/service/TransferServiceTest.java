package com.tokiomarine.financial_transfer_scheduler.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.tokiomarine.financial_transfer_scheduler.dto.TransferRequestDTO;
import com.tokiomarine.financial_transfer_scheduler.dto.TransferResponseDTO;
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
    void testSchedule_successful() {
        TransferRequestDTO dto = new TransferRequestDTO("123", "456", BigDecimal.valueOf(1000), LocalDate.now());
        when(repository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        TransferResponseDTO result = transferService.schedule(dto);

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
