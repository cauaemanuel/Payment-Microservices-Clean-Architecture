package com.payment_api_service.application.ports.output;

import com.payment_api_service.domain.entity.Transaction;

public interface PaymentEventPublisher {

    void processPayment(Transaction transaction);

}
