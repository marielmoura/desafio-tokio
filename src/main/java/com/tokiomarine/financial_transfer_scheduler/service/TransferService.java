package com.tokiomarine.financial_transfer_scheduler.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokiomarine.financial_transfer_scheduler.dto.TransferRequestDTO;
import com.tokiomarine.financial_transfer_scheduler.dto.TransferResponseDTO;
import com.tokiomarine.financial_transfer_scheduler.model.Transfer;
import com.tokiomarine.financial_transfer_scheduler.repository.TransferRepository;

@Service
public class TransferService {

	@Autowired
	TransferRepository repository;

	public TransferResponseDTO schedule(TransferRequestDTO dto) {

		Transfer transfer = new Transfer(
				dto.getSourceAccount(),
				dto.getDestinationAccount(),
				dto.getAmount(),
				dto.getTransferDate());

		return repository.save(transfer).toDto();
	}

	public Set<TransferResponseDTO> listAll() {
		return repository.findAll()
				.stream()
				.map(Transfer::toDto)
				.collect(Collectors.toSet());
	}
}
