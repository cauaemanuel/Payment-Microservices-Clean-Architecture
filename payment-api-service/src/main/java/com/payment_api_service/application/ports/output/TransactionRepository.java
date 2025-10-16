package com.payment_api_service.application.ports.output;

import com.payment_api_service.domain.entity.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(UUID id);
}
