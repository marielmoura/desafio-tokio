package com.tokiomarine.financial_transfer_scheduler.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.tokiomarine.financial_transfer_scheduler.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
	@Override
	@NonNull
	Page<Transfer> findAll(Pageable pageable);
}
