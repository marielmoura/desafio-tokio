package com.tokiomarine.financial_transfer_scheduler.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.tokiomarine.financial_transfer_scheduler.dto.TransferResponseDTO;
import com.tokiomarine.financial_transfer_scheduler.exception.InvalidTransferException;

@Entity
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceAccount;
    private String destinationAccount;

    private BigDecimal amount;
    private BigDecimal fee;

    private LocalDate transferDate;
    private LocalDate schedulingDate;

    public Transfer() {
    }

    public Transfer(String sourceAccount, String destinationAccount, BigDecimal amount,
            LocalDate transferDate) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.transferDate = transferDate;

        this.schedulingDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(this.schedulingDate, transferDate);
        this.calculateFee(daysBetween);
    }

    public Long getId() {
        return id;
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

    public BigDecimal getFee() {
        return fee;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public LocalDate getSchedulingDate() {
        return schedulingDate;
    }

    private void calculateFee(long daysBetween) {
        if (daysBetween == 0) {
            this.fee = BigDecimal.valueOf(3.00)
                    .add(this.amount.multiply(BigDecimal.valueOf(0.025)))
                    .setScale(2, RoundingMode.HALF_UP);
        } else if (daysBetween >= 1 && daysBetween <= 10) {
            this.fee = BigDecimal.valueOf(12.00).setScale(2, RoundingMode.HALF_UP);
        } else if (daysBetween >= 11 && daysBetween <= 20) {
            this.fee = this.amount.multiply(BigDecimal.valueOf(0.082)).setScale(2, RoundingMode.HALF_UP);
        } else if (daysBetween >= 21 && daysBetween <= 30) {
            this.fee = this.amount.multiply(BigDecimal.valueOf(0.069)).setScale(2, RoundingMode.HALF_UP);
        } else if (daysBetween >= 31 && daysBetween <= 40) {
            this.fee = this.amount.multiply(BigDecimal.valueOf(0.047)).setScale(2, RoundingMode.HALF_UP);
        } else if (daysBetween >= 41 && daysBetween <= 50) {
            this.fee = this.amount.multiply(BigDecimal.valueOf(0.017)).setScale(2, RoundingMode.HALF_UP);
        } else {
            throw new InvalidTransferException("Não há taxa aplicável para o número de dias informado.");
        }
    }

    public TransferResponseDTO toDto() {
        return new TransferResponseDTO(
            this.id,
            this.sourceAccount,
            this.destinationAccount,
            this.amount,
            this.fee,
            this.transferDate,
            this.schedulingDate
        );
    }


}
