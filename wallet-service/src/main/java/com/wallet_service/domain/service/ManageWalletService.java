package com.wallet_service.domain.service;
import com.wallet_service.application.ports.input.ManageWalletPort;
import com.wallet_service.application.ports.output.UserClient;
import com.wallet_service.domain.entity.Wallet;
import com.wallet_service.application.ports.output.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManageWalletService implements ManageWalletPort {

    private final WalletRepository walletRepository;
    private final UserClient userClient;

    public Double getWalletBalance(String token) {
        var userEmail = getEmail(token);
        var wallet = findWalletByUserEmail(userEmail);
        return wallet.getBalance();
    }

    @Transactional
    public void updateWalletBalance(String token, Double newBalance) {
         var userEmail = getEmail(token);

        if (newBalance < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New balance cannot be negative");
        }

        var wallet = findWalletByUserEmail(userEmail);
        wallet.setBalance(wallet.getBalance() + newBalance);
        walletRepository.save(wallet);
    }

    public boolean verifyAmount(String email, Double amount) {

        if (amount == null || amount <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be greater than zero");
        }

        var wallet = findWalletByUserEmail(email);
        return wallet.getBalance() >= amount;
    }

    public boolean isWalletExists(String email) {
        return walletRepository.existsByUserEmail(email);
    }

    private String getEmail(String token) {
        var email = userClient.emailByToken(token);
        if (email == null || email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token or user not found");
        }
        return email;
    }

    private Wallet findWalletByUserEmail(String userEmail) {
        return walletRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found for user Email: " + userEmail));
    }

}
