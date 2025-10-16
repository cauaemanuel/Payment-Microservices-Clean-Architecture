package com.wallet_service.adapters.output.messaging;

import com.wallet_service.application.dto.TransactionMessageDto;
import com.wallet_service.application.ports.output.WalletEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitWalletEventPublisher implements WalletEventPublisher {

    @Value("${exchange.name}")
    private String paymentExchange;

    @Value("${routing.key}")
    private String paymentAcceptedRoutingKey ;

    private RabbitTemplate rabbitTemplate;

    public RabbitWalletEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processSucessfulPayment(TransactionMessageDto dto){
        log.info("Sending successful payment message: {}", dto);
        rabbitTemplate.convertAndSend(paymentExchange, paymentAcceptedRoutingKey, dto);
    }

}
