package com.tokiomarine.financial_transfer_scheduler.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
	private TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponseDTO> schedule(@Valid @RequestBody TransferRequestDTO dto) {
        TransferResponseDTO transfer = transferService.schedule(dto);
        return new ResponseEntity<>(transfer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<TransferResponseDTO>> listAll() {
        Set<TransferResponseDTO> transfers = transferService.listAll();
        return ResponseEntity.ok(transfers);
    }
}
