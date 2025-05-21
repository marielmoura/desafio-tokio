package com.tokiomarine.financial_transfer_scheduler.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransferRequestDTO {

	@NotBlank(message = "{sourceAccount.notblank}")
	private String sourceAccount;

	@NotBlank(message = "{destinationAccount.notblank}")
	private String destinationAccount;

	@NotNull(message = "{amount.notnull}")
	@DecimalMin(value = "0.01", message = "{amount.min}")
	private BigDecimal amount;

	@NotNull
	@FutureOrPresent(message = "{transferDate.futureOrPresent}")
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
