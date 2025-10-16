package com.user_service.domain.service;

import com.user_service.application.ports.input.GetEmailByTokenUseCase;
import com.user_service.infrastructure.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetEmailByTokenUseCaseImpleTest {

    @InjectMocks
    private GetEmailByTokenUseCaseImple getEmailByTokenUseCase;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class ExecuteMethod {

        @org.junit.jupiter.api.Test
        void shouldThrowIllegalArgumentExceptionWhenTokenIsNull() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                getEmailByTokenUseCase.execute(null);
            });
            assertEquals("Token cannot be null or empty", exception.getMessage());
        }

        @org.junit.jupiter.api.Test
        void shouldThrowIllegalArgumentExceptionWhenTokenIsEmpty() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                getEmailByTokenUseCase.execute("");
            });
            assertEquals("Token cannot be null or empty", exception.getMessage());
        }
    }



}