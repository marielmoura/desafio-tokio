package com.tokiomarine.financial_transfer_scheduler.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferRequestDTO {

	private String sourceAccount;
	private String destinationAccount;
	private BigDecimal amount;
	private LocalDate transferDate;

	public TransferRequestDTO() {
	}

	public TransferRequestDTO(String sourceAccount, String destinationAccount, BigDecimal amount,
			LocalDate transferDate) {
		this.sourceAccount = sourceAccount;
		this.destinationAccount = destinationAccount;
		this.amount = amount;
		this.transferDate = transferDate;
	}

	public String getSourceAccount() {
		return sourceAccount;
	}

	public String getDestinationAccount() {
		return destinationAccount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public LocalDate getTransferDate() {
		return transferDate;
	}

}
