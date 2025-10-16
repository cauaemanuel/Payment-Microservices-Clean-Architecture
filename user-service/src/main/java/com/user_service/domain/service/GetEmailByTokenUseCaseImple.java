package com.user_service.domain.service;

import com.user_service.application.ports.input.GetEmailByTokenUseCase;
import com.user_service.infrastructure.security.TokenService;

public class GetEmailByTokenUseCaseImple implements GetEmailByTokenUseCase {

    private TokenService tokenService;

    public GetEmailByTokenUseCaseImple(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String execute(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        return tokenService.getSubjectFromToken(token);
    }
}
