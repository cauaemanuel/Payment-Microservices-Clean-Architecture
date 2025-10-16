package com.payment_api_service.adapters.output.persistence;

import com.payment_api_service.domain.entity.Transaction;
import com.payment_api_service.application.ports.output.TransactionRepository;

import java.util.Optional;
import java.util.UUID;

public class TransactionRepositoryImple implements TransactionRepository {

    private SpringDataTransactionRepository springDataTransactionRepository;

    public TransactionRepositoryImple(SpringDataTransactionRepository springDataTransactionRepository) {
        this.springDataTransactionRepository = springDataTransactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = toEntity(transaction);
        TransactionEntity savedEntity = springDataTransactionRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return springDataTransactionRepository.findById(id).map(this::toDomain);
    }

    Transaction toDomain(TransactionEntity entity) {
        Transaction transaction = new Transaction();
        transaction.setId(entity.getId());
        transaction.setSourceUserEmail(entity.getSourceUserId());
        transaction.setDestinationUserEmail(entity.getDestinationUserId());
        transaction.setAmount(entity.getAmount());
        transaction.setStatus(entity.getStatus());
        transaction.setCreatedAt(entity.getCreatedAt());
        return transaction;
    }

    TransactionEntity toEntity(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId());
        entity.setSourceUserId(transaction.getSourceUserEmail());
        entity.setDestinationUserId(transaction.getDestinationUserEmail());
        entity.setAmount(transaction.getAmount());
        entity.setStatus(transaction.getStatus());
        entity.setCreatedAt(transaction.getCreatedAt());
        return entity;
    }
}
