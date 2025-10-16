package com.payment_api_service.infrastructure.messaging;

import com.payment_api_service.application.dto.TransactionMessageDto;
import com.payment_api_service.domain.entity.Transaction;
import com.payment_api_service.application.ports.output.PaymentEventPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitPaymentEventPublisher implements PaymentEventPublisher {

    private RabbitTemplate rabbitTemplate;

    @Value("${exchange.name}")
    private String paymentExchange;

    @Value("${routing.key.transfer}")
    private String paymentRoutingKey ;

    public RabbitPaymentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processPayment(Transaction transaction) {
        var transferDto = new TransactionMessageDto();
        transferDto.setId(transaction.getId());
        transferDto.setSenderUserId(transaction.getSourceUserEmail());
        transferDto.setRecipientUserId(transaction.getDestinationUserEmail());
        transferDto.setAmount(transaction.getAmount());

        rabbitTemplate.convertAndSend(paymentExchange, paymentRoutingKey, transferDto);
    }

}


