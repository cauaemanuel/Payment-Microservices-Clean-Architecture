package com.wallet_service.adapters.output.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", fallback = SpringUserClienteFallback.class)
public interface SpringUserClient {

    @GetMapping("/users/exists")
    Boolean exists(@RequestParam("userEmail") String userEmail);

    @GetMapping("/users/email-by-token")
    String emailByToken(@RequestParam("token") String token);
}