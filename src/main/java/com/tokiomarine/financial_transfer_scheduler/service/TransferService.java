package com.tokiomarine.financial_transfer_scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<TransferResponseDTO> listAll(Pageable pageable) {
		return repository.findAll(pageable)
				.map(Transfer::toDto);
	}

}
