package com.payment_api_service.domain.service;

import com.payment_api_service.application.ports.input.InitiateTransferPort;
import com.payment_api_service.application.ports.output.UserClient;
import com.payment_api_service.application.ports.output.WalletClient;
import com.payment_api_service.domain.entity.Transaction;
import com.payment_api_service.application.ports.output.PaymentEventPublisher;
import com.payment_api_service.application.ports.output.TransactionRepository;
import com.payment_api_service.domain.enums.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Slf4j
@Component
public class InitiateTransferUseCase implements InitiateTransferPort {

    private WalletClient walletClient;
    private PaymentEventPublisher rabbitPaymentEventPublisher;
    private TransactionRepository transactionRepository;
    private UserClient userClient;

   public InitiateTransferUseCase(
            WalletClient walletClient,
            PaymentEventPublisher rabbitPaymentEventPublisher,
            TransactionRepository transactionRepository,
            UserClient userClient) {
        this.walletClient = walletClient;
        this.rabbitPaymentEventPublisher = rabbitPaymentEventPublisher;
        this.transactionRepository = transactionRepository;
        this.userClient = userClient;
    }

    public void execute(String destinationEmail, String token, double amount) {

       var sourceEmail = userClient.emailByToken(token);

        log.info("Initiating transfer from source ID: {} to destination ID: {} with amount: {}", sourceEmail, destinationEmail, amount);
        if (destinationEmail == null || destinationEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination ID cannot be null or empty");
        }
        if (sourceEmail == null || sourceEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Source ID cannot be null or empty");
        }
        if (amount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be greater than zero");
        }


        var transaction = new Transaction();
        transaction.setSourceUserEmail(sourceEmail);
        transaction.setDestinationUserEmail(destinationEmail);
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.PROCESSING);
        transaction.setCreatedAt(LocalDateTime.now());

        var transactionSave = transactionRepository.save(transaction);

        rabbitPaymentEventPublisher.processPayment(transactionSave);
    }
}
