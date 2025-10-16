package com.payment_api_service.infrastructure.messaging;

import com.payment_api_service.application.dto.TransactionMessageDto;
import com.payment_api_service.application.ports.input.TransactionResultPort;
import com.payment_api_service.domain.enums.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitPaymentEventConsumer {

    private TransactionResultPort transactionResultUseCase;

    public RabbitPaymentEventConsumer(TransactionResultPort transactionResultUseCase) {
        this.transactionResultUseCase = transactionResultUseCase;
    }

    @RabbitListener(queues = "payment.rejected")
    public void processRejectedTransaction(TransactionMessageDto transactionMessageDto) {
        log.info("Processing rejected transaction: {}", transactionMessageDto);
        transactionResultUseCase.processRejectedTransaction(transactionMessageDto);
    }

    @RabbitListener(queues = "payment.accepted")
    public void processSucessfulTransaction(TransactionMessageDto transactionMessageDto) {
        log.info("Processing successful transaction: {}", transactionMessageDto);
        transactionResultUseCase.processAcceptedTransaction(transactionMessageDto);
    }

    @RabbitListener(queues = "payment.dlq")
    public void processFailedTransaction(TransactionMessageDto transactionMessageDto) {
        log.info("Processing failed transaction: {}", transactionMessageDto);
        transactionResultUseCase.processRejectedTransaction(transactionMessageDto);
    }
}
