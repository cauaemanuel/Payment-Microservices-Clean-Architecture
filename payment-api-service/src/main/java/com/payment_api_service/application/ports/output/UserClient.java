package com.payment_api_service.application.ports.output;

public interface UserClient {

    String emailByToken(String token);
}
