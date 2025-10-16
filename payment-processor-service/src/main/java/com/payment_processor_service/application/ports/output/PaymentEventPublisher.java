package com.payment_processor_service.application.ports.output;

import com.payment_processor_service.entity.TransactionMessageDto;

public interface PaymentEventPublisher {
    void sendAcceptedTransactionMessage(TransactionMessageDto dto);
    void sendRejectedTransactionMessage(TransactionMessageDto dto);
}
