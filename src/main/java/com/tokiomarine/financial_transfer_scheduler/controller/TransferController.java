package com.tokiomarine.financial_transfer_scheduler.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokiomarine.financial_transfer_scheduler.dto.TransferRequestDTO;
import com.tokiomarine.financial_transfer_scheduler.dto.TransferResponseDTO;
import com.tokiomarine.financial_transfer_scheduler.service.TransferService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    @Operation(summary = "Agenda uma transferência financeira")
    public ResponseEntity<TransferResponseDTO> schedule(@Valid @RequestBody TransferRequestDTO dto) {
        TransferResponseDTO transfer = transferService.schedule(dto);
        return new ResponseEntity<>(transfer, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lista todas as transferências agendadas")
    public ResponseEntity<Page<TransferResponseDTO>> listAll(Pageable pageable) {
        Page<TransferResponseDTO> transfers = transferService.listAll(pageable);
        return ResponseEntity.ok(transfers);
    }
}
