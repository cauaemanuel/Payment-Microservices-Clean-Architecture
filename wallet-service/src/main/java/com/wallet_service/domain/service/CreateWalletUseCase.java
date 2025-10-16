package com.wallet_service.domain.service;

import com.wallet_service.application.ports.input.CreateWalletPort;
import com.wallet_service.application.ports.output.UserClient;
import com.wallet_service.application.ports.output.WalletRepository;
import com.wallet_service.domain.entity.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
public class CreateWalletUseCase implements CreateWalletPort {

    private WalletRepository walletRepository;
    private UserClient userClient;

    public CreateWalletUseCase(WalletRepository walletRepository, UserClient userClient) {
        this.walletRepository = walletRepository;
        this.userClient = userClient;
    }

    @Transactional
    public void createWallet(String token) {

        var userEmail = userClient.emailByToken(Objects.requireNonNull(token));

        if (userEmail == null || userEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User email cannot be null or empty");
        }

        if (walletRepository.existsByUserEmail(userEmail)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wallet already exists for user email: ");
        }

        if(!userClient.exists(userEmail)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + userEmail);
        }

        var wallet = new Wallet();
        wallet.setUserEmail(userEmail);
        wallet.setBalance(0.0);
        walletRepository.save(wallet);
    }

}
