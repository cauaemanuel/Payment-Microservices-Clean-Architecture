package com.wallet_service.adapters.output.persistence;

import com.wallet_service.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringJpaWalletRepository extends JpaRepository<WalletEntity, UUID> {

    Optional<WalletEntity> findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);
}