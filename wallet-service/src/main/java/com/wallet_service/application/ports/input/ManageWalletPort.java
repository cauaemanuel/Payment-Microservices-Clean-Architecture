package com.wallet_service.application.ports.input;

public interface ManageWalletPort {
    Double getWalletBalance(String token);
    void updateWalletBalance(String token, Double newBalance);
    boolean verifyAmount(String email, Double amount);
    boolean isWalletExists(String email);
}
