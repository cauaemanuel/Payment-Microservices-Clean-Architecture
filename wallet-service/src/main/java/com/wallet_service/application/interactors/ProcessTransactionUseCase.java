package com.wallet_service.application.interactors;

import com.wallet_service.application.dto.TransactionMessageDto;
import com.wallet_service.domain.messaging.WalletEventPublisher;
import com.wallet_service.domain.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessTransactionUseCase {

    private WalletRepository walletRepository;
    private WalletEventPublisher walletEventPublisher;

    public ProcessTransactionUseCase(WalletRepository walletRepository, WalletEventPublisher walletEventPublisher) {
        this.walletRepository = walletRepository;
        this.walletEventPublisher = walletEventPublisher;
    }

    public void processTransaction(TransactionMessageDto dto){
        var sender = walletRepository.findByUserId(dto.getSenderUserId());
        var receiver = walletRepository.findByUserId(dto.getRecipientUserId());

        if (sender.isEmpty() || receiver.isEmpty()) {
            throw new IllegalArgumentException("Sender or receiver wallet not found");
            //enviar pra dlq
        }

        if (sender.get().getBalance() < dto.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance in sender's wallet");
            //enviar pra dlq
        }

        // Reduz o saldo de quem enviou
        sender.get().setBalance(sender.get().getBalance() - dto.getAmount());
        log.info("Deducted {} from sender's wallet. New balance: {}", dto.getAmount(), sender.get().getBalance());
        // Adiciona o saldo em quem recebeu
        receiver.get().setBalance(receiver.get().getBalance() + dto.getAmount());
        log.info("Added {} to receiver's wallet. New balance: {}", dto.getAmount(), receiver.get().getBalance());

        // Salva as alterações nos wallets
        walletRepository.save(sender.get());
        walletRepository.save(receiver.get());

        // coloca na fila de sucesso
        walletEventPublisher.processSucessfulPayment(dto);
    }
}
