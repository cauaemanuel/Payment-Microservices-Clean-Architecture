package com.payment_processor_service.application.ports.input;

import com.payment_processor_service.entity.TransactionMessageDto;

public interface ProcessPaymentPort {
    void processPayment(TransactionMessageDto dto);
}
