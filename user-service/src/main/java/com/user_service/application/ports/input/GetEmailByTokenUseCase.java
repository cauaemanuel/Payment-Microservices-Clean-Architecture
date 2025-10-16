package com.user_service.application.ports.input;

public interface GetEmailByTokenUseCase {

    String execute(String token);
}
