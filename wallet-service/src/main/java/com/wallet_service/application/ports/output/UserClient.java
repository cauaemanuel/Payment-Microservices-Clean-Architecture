package com.wallet_service.application.ports.output;

public interface UserClient {

    Boolean exists(String userEmail);

    String emailByToken(String token);
}
