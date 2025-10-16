package com.payment_processor_service.infrastructure.messaging;

import com.payment_processor_service.entity.TransactionMessageDto;
import com.payment_processor_service.application.ports.input.ProcessPaymentPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentProcessorConsumer {

    private ProcessPaymentPort processPaymentPort;

    public PaymentProcessorConsumer(ProcessPaymentPort processPaymentPort) {
        this.processPaymentPort = processPaymentPort;
    }

    @RabbitListener(queues = "payment.transfer")
    public void receiveMessage(TransactionMessageDto transaction) {
        log.info("Received transaction: {}", transaction);
        processPaymentPort.processPayment(transaction);
    }
}
