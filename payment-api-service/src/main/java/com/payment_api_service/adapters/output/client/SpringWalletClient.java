package com.payment_api_service.adapters.output.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wallet-service", fallback = SpringUserClienteFallback.class)
public interface SpringWalletClient {

    @GetMapping("/wallet/{id}/verify-amount")
    boolean verifyAmount(@RequestParam String email, @RequestParam Double amount);

    @GetMapping("/wallet/exists")
    boolean exists(@RequestParam String email);
}
