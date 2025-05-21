package com.tokiomarine.financial_transfer_scheduler.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public Transfer(String sourceAccount, String destinationAccount, BigDecimal amount, BigDecimal fee,
            LocalDate transferDate, LocalDate schedulingDate) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.fee = fee;
        this.transferDate = transferDate;
        this.schedulingDate = schedulingDate;
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

}
