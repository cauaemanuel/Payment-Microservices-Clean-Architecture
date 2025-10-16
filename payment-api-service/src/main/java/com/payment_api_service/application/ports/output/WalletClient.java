package com.payment_api_service.application.ports.output;


public interface WalletClient {

    boolean exists(String userId);
    boolean verifyAmount( String id, Double amount);
}
