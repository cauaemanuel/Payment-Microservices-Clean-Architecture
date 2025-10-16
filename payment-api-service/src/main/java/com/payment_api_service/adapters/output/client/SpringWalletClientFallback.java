package com.payment_api_service.adapters.output.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SpringWalletClientFallback implements SpringWalletClient {
    @Override
    public boolean verifyAmount(String email, Double amount) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet service is not available"); // Fallback response when wallet service is unavailable
    }

    @Override
    public boolean exists(String email) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet service is not available"); // Fallback response when wallet service is unavailable
    }
}
