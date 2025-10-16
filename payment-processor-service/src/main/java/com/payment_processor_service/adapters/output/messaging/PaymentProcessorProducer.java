package com.payment_processor_service.adapters.output.messaging;

import com.payment_processor_service.application.ports.output.PaymentEventPublisher;


import com.payment_processor_service.entity.TransactionMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentProcessorProducer implements PaymentEventPublisher {

    private RabbitTemplate rabbitTemplate;

    @Value("${exchange.name}")
    private String paymentExchange;

    @Value("${wallet-exchange.name}")
    private String walletExchange;

    @Value("${routing.key.rejected}")
    private String paymentRejectedRoutingKey;

    @Value("${wallet-routing.key.accepted}")
    private String paymentAcceptedWalletRoutingKey;

    public PaymentProcessorProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAcceptedTransactionMessage(TransactionMessageDto dto) {
        log.info("Sending accepted transaction message: {}", dto);
        log.debug("Sending message to exchange: {}, routing key: {}, message: {}", walletExchange, paymentAcceptedWalletRoutingKey, dto);
        rabbitTemplate.convertAndSend(walletExchange, paymentAcceptedWalletRoutingKey, dto);
    }

    public void sendRejectedTransactionMessage(TransactionMessageDto dto) {
        log.info("Sending rejected transaction message: {}", dto);
        log.debug("Sending message to exchange: {}, routing key: {}, message: {}", paymentExchange, paymentRejectedRoutingKey, dto);
        rabbitTemplate.convertAndSend(paymentExchange, paymentRejectedRoutingKey, dto);
    }
}
