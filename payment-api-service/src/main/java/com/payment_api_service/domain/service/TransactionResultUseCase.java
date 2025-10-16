package com.payment_api_service.domain.service;

import com.payment_api_service.application.dto.TransactionMessageDto;
import com.payment_api_service.application.ports.input.TransactionResultPort;
import com.payment_api_service.domain.entity.Transaction;
import com.payment_api_service.domain.enums.TransactionStatus;
import com.payment_api_service.application.ports.output.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionResultUseCase implements TransactionResultPort {

    private TransactionRepository transactionRepository;

    public TransactionResultUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void processAcceptedTransaction(TransactionMessageDto dto) {
        execute(dto, TransactionStatus.APPROVED);
    }

    @Override
    public void processRejectedTransaction(TransactionMessageDto dto) {
        execute(dto, TransactionStatus.REJECTED);
    }

    private void execute(TransactionMessageDto dto, TransactionStatus status) {
        log.info("Processing transaction with ID: {}, Status: {}", dto.getId(), status);
        Transaction transaction = transactionRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        log.info("Processing transaction: {}", dto);
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }
}
