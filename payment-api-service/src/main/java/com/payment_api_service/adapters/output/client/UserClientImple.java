package com.payment_api_service.adapters.output.client;

import com.payment_api_service.application.ports.output.UserClient;

public class UserClientImple implements UserClient {

    private SpringUserClient springUserClient;

    public UserClientImple(SpringUserClient springUserClient) {
        this.springUserClient = springUserClient;
    }

    @Override
    public String emailByToken(String token) {
        return springUserClient.emailByToken(token);
    }
}