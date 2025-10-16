package com.payment_api_service.adapters.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataTransactionRepository extends JpaRepository<TransactionEntity, UUID> {

}
