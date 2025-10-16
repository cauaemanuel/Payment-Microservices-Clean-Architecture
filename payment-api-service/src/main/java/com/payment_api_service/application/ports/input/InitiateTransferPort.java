package com.payment_api_service.application.ports.input;

public interface InitiateTransferPort {
    void execute(String destinationEmail, String token, double amount);
}
