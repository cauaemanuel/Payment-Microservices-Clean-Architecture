package com.payment_api_service.adapters.output.client;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SpringUserClienteFallback implements SpringUserClient {

    @Override
    public String emailByToken(String token) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User service is not available");
    }
}
