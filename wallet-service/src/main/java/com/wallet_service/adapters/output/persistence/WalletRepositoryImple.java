package com.wallet_service.adapters.output.persistence;

import com.wallet_service.domain.entity.Wallet;
import com.wallet_service.application.ports.output.WalletRepository;

import java.util.Optional;

public class WalletRepositoryImple implements WalletRepository {

    private final SpringJpaWalletRepository springJpaWalletRepository;

    public WalletRepositoryImple(SpringJpaWalletRepository springJpaWalletRepository) {
        this.springJpaWalletRepository = springJpaWalletRepository;
    }


    @Override
    public Optional<Wallet> save(Wallet wallet) {
        WalletEntity entity = toEntity(wallet);
        WalletEntity savedEntity = springJpaWalletRepository.save(entity);
        return Optional.of(toDomain(savedEntity));
    }

    @Override
    public Optional<Wallet> findByUserEmail(String userEmail) {
        return springJpaWalletRepository.findByUserEmail(userEmail).map(this::toDomain);
    }

    @Override
    public boolean existsByUserEmail(String userEmail) {
        return springJpaWalletRepository.existsByUserEmail(userEmail);
    }

    Wallet toDomain(WalletEntity entity) {
        Wallet wallet = new Wallet();
        wallet.setId(entity.getId());
        wallet.setUserEmail(entity.getUserEmail());
        wallet.setBalance(entity.getBalance());
        return wallet;
    }

    WalletEntity toEntity(Wallet wallet) {
        WalletEntity entity = new WalletEntity();
        entity.setId(wallet.getId());
        entity.setUserEmail(wallet.getUserEmail());
        entity.setBalance(wallet.getBalance());
        return entity;
    }
}
