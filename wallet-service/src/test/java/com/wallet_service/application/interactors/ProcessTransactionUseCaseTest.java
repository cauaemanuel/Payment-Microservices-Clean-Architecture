package com.wallet_service.application.interactors;

import com.wallet_service.application.dto.TransactionMessageDto;
import com.wallet_service.domain.entity.Wallet;
import com.wallet_service.domain.messaging.WalletEventPublisher;
import com.wallet_service.domain.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ProcessTransactionUseCaseTest {

    @Mock
    WalletRepository walletRepository;

    @Mock
    WalletEventPublisher walletEventPublisher;

    ProcessTransactionUseCase processTransactionUseCase;

    TransactionMessageDto transactionMessageDto = new TransactionMessageDto();
    Wallet senderWallet = new Wallet();
    Wallet receiverWallet = new Wallet();

    @BeforeEach
    void setUp() {
        processTransactionUseCase = new ProcessTransactionUseCase(walletRepository, walletEventPublisher);
        transactionMessageDto.setId(UUID.randomUUID());
        transactionMessageDto.setSenderUserId(String.valueOf(UUID.randomUUID()));
        transactionMessageDto.setRecipientUserId(String.valueOf(UUID.randomUUID()));
        transactionMessageDto.setAmount(100.0);

        senderWallet.setUserEmail(transactionMessageDto.getSenderUserId());
        senderWallet.setId(UUID.fromString(transactionMessageDto.getSenderUserId()));
        senderWallet.setBalance(0.0);

        receiverWallet.setUserEmail(transactionMessageDto.getRecipientUserId());
        receiverWallet.setId(UUID.fromString(transactionMessageDto.getRecipientUserId()));
        receiverWallet.setBalance(0.0);
    }

    @Test
    void deveBarrarTransacaoQuandoCarteiraInexistente() {
        // Arrange
        Mockito.when(walletRepository.findByUserEmail(anyString()))
                .thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            processTransactionUseCase.processTransaction(transactionMessageDto);
        });
        assertEquals("Sender or receiver wallet not found", exception.getMessage());

        Mockito.verify(walletRepository, Mockito.times(1)).findByUserEmail(transactionMessageDto.getSenderUserId());
        Mockito.verify(walletRepository, Mockito.times(1)).findByUserEmail(transactionMessageDto.getRecipientUserId());
    }

    @Test
    void deveBarrarTransacaoQuandoSaldoInsuficiente(){
        // Arrange
        Mockito.when(walletRepository.findByUserEmail(transactionMessageDto.getSenderUserId()))
                .thenReturn(Optional.of(senderWallet));

        Mockito.when(walletRepository.findByUserEmail(transactionMessageDto.getRecipientUserId()))
                .thenReturn(Optional.of(receiverWallet));
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            processTransactionUseCase.processTransaction(transactionMessageDto);
        });
        assertEquals("Insufficient balance in sender's wallet", exception.getMessage());
        Mockito.verify(walletRepository, Mockito.times(1)).findByUserEmail(transactionMessageDto.getSenderUserId());
        Mockito.verify(walletRepository, Mockito.times(1)).findByUserEmail(transactionMessageDto.getRecipientUserId());
    }

    @Test
    void deveAtualizarOSaldoDasCarteirasQuandoTransacaoForSucesso(){
        // Arrange
        senderWallet.setBalance(200.0);

        Mockito.when(walletRepository.findByUserEmail(transactionMessageDto.getSenderUserId()))
                .thenReturn(Optional.of(senderWallet));

        Mockito.when(walletRepository.findByUserEmail(transactionMessageDto.getRecipientUserId()))
                .thenReturn(Optional.of(receiverWallet));
        // Act
        processTransactionUseCase.processTransaction(transactionMessageDto);
        // Assert
        assertEquals(100.0, senderWallet.getBalance());
        assertEquals(100.0, receiverWallet.getBalance());

        Mockito.verify(walletRepository, Mockito.times(1)).findByUserEmail(transactionMessageDto.getSenderUserId());
        Mockito.verify(walletRepository, Mockito.times(1)).findByUserEmail(transactionMessageDto.getRecipientUserId());
        Mockito.verify(walletRepository, Mockito.times(1)).save(senderWallet);
        Mockito.verify(walletRepository, Mockito.times(1)).save(receiverWallet);
        Mockito.verify(walletEventPublisher, Mockito.times(1)).processSucessfulPayment(transactionMessageDto);
    }

    @Test
    void deveBarrarTransacaoQuandoValorInvalido(){
        // Arrange
        transactionMessageDto.setAmount(-10.0);
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            processTransactionUseCase.processTransaction(transactionMessageDto);
        });
        assertEquals("Transaction amount must be greater than zero", exception.getMessage());
    }

}
