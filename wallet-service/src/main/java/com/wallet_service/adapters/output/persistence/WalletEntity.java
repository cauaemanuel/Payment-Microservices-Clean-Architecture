package com.wallet_service.adapters.output.persistence;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity(name = "tb_wallet")
@Data
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String userEmail;

    private Double balance;

}
