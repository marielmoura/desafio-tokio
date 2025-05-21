package com.tokiomarine.financial_transfer_scheduler.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tokiomarine.financial_transfer_scheduler.dto.TransferRequestDTO;
import com.tokiomarine.financial_transfer_scheduler.exception.InvalidTransferException;
import com.tokiomarine.financial_transfer_scheduler.model.Transfer;
import com.tokiomarine.financial_transfer_scheduler.repository.TransferRepository;

public class TransferService {

	@Autowired
	TransferRepository repository;

	public Transfer schedule(TransferRequestDTO dto) {
		LocalDate today = LocalDate.now();
		long daysBetween = ChronoUnit.DAYS.between(today, dto.getTransferDate());

		BigDecimal fee = calculateFee(dto.getAmount(), daysBetween);

		Transfer transfer = new Transfer(
				dto.getSourceAccount(),
				dto.getDestinationAccount(),
				dto.getAmount(),
				fee,
				dto.getTransferDate(),
				today);

		return repository.save(transfer);
	}

	public List<Transfer> listAll() {
		return repository.findAll();
	}

	public BigDecimal calculateFee(BigDecimal amount, long daysBetween) {
		if (daysBetween == 0)
			return BigDecimal.valueOf(3.00)
					.add(amount.multiply(BigDecimal.valueOf(0.025)))
					.setScale(2, RoundingMode.HALF_UP);

		if (daysBetween >= 1 && daysBetween <= 10)
			return BigDecimal.valueOf(12.00).setScale(2, RoundingMode.HALF_UP);

		if (daysBetween >= 11 && daysBetween <= 20)
			return amount.multiply(BigDecimal.valueOf(0.082)).setScale(2, RoundingMode.HALF_UP);

		if (daysBetween >= 21 && daysBetween <= 30)
			return amount.multiply(BigDecimal.valueOf(0.069)).setScale(2, RoundingMode.HALF_UP);

		if (daysBetween >= 31 && daysBetween <= 40)
			return amount.multiply(BigDecimal.valueOf(0.047)).setScale(2, RoundingMode.HALF_UP);

		if (daysBetween >= 41 && daysBetween <= 50)
			return amount.multiply(BigDecimal.valueOf(0.017)).setScale(2, RoundingMode.HALF_UP);

		throw new InvalidTransferException("Não há taxa aplicável para o número de dias informado.");
	}

}
