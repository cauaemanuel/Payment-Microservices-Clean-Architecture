package com.payment_api_service.adapters.output.client;

import com.payment_api_service.application.ports.output.WalletClient;

public class WalletClientImple implements WalletClient {

    private SpringWalletClient springWalletClient;

    public WalletClientImple(SpringWalletClient springWalletClient) {
        this.springWalletClient = springWalletClient;
    }

    @Override
    public boolean exists(String email) {
        return springWalletClient.exists(email);
    }

    @Override
    public boolean verifyAmount(String email, Double amount) {
        return springWalletClient.verifyAmount(email, amount);
    }
}
