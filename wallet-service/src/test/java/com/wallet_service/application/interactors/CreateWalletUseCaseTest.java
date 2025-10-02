package com.wallet_service.application.interactors;

import com.wallet_service.domain.client.UserClient;
import com.wallet_service.domain.repository.WalletRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CreateWalletUseCaseTest {

    @Mock
    WalletRepository walletRepository;
    @Mock
    UserClient userClient;

    CreateWalletUseCase createWalletUseCase;

    @BeforeEach
    void setUp() {
        createWalletUseCase = new CreateWalletUseCase(walletRepository, userClient);
    }

    @Test
    void deveBarrarSeTokenForNulo() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> createWalletUseCase.createWallet(null));
        Mockito.verifyNoInteractions(userClient);
        Mockito.verifyNoInteractions(walletRepository);
    }

    @Test
    void deveBarrarSeUsuarioNaoExistir() {
        // Arrange
        Mockito.when(userClient.emailByToken(anyString())).thenReturn(null);
        // Act & Assert
        var exception = assertThrows(ResponseStatusException.class, () -> createWalletUseCase.createWallet("token"));
        assertTrue(exception.getMessage().contains("User email cannot be null or empty"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        Mockito.verify(userClient, Mockito.times(1)).emailByToken(anyString());
    }

    @Test
    void deveBarrarSeCarteiraJaExistir() {
        // Arrange
        Mockito.when(userClient.emailByToken(anyString())).thenReturn("email");
        Mockito.when(walletRepository.existsByUserEmail(anyString())).thenReturn(true);
        // Act & Assert
        var exception = assertThrows(ResponseStatusException.class, () -> createWalletUseCase.createWallet("token"));
        assertTrue(exception.getMessage().contains("Wallet already exists for user email: "));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        Mockito.verify(userClient, Mockito.times(1)).emailByToken(anyString());
        Mockito.verify(walletRepository, Mockito.times(1)).existsByUserEmail(anyString());
    }

    @Test
    void deveBarrarSeUsuarioNaoForEncontrado() {
        // Arrange
        Mockito.when(userClient.emailByToken(anyString())).thenReturn("email");
        Mockito.when(walletRepository.existsByUserEmail(anyString())).thenReturn(false);
        Mockito.when(userClient.exists(anyString())).thenReturn(false);
        // Act & Assert
        var exception = assertThrows(ResponseStatusException.class, () -> createWalletUseCase.createWallet("token"));

        assertTrue(exception.getMessage().contains("User not found with email: email"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        Mockito.verify(userClient, Mockito.times(1)).emailByToken(anyString());
        Mockito.verify(walletRepository, Mockito.times(1)).existsByUserEmail(anyString());
        Mockito.verify(userClient, Mockito.times(1)).exists(anyString());
    }

    @Test
    void deveCriarCarteiraComSucesso() {
        // Arrange
        Mockito.when(userClient.emailByToken(anyString())).thenReturn("email");
        Mockito.when(walletRepository.existsByUserEmail(anyString())).thenReturn(false);
        Mockito.when(userClient.exists(anyString())).thenReturn(true);
        // Act
        assertDoesNotThrow(() -> createWalletUseCase.createWallet("token"));
        // Assert
        Mockito.verify(userClient, Mockito.times(1)).emailByToken(anyString());
        Mockito.verify(walletRepository, Mockito.times(1)).existsByUserEmail(anyString());
        Mockito.verify(userClient, Mockito.times(1)).exists(anyString());
        Mockito.verify(walletRepository, Mockito.times(1)).save(Mockito.any());
    }
}