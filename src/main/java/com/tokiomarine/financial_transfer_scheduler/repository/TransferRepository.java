package com.tokiomarine.financial_transfer_scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tokiomarine.financial_transfer_scheduler.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
