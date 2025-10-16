package com.wallet_service.infrastructure.config;

import com.wallet_service.application.ports.output.UserClient;
import com.wallet_service.application.ports.output.WalletEventPublisher;
import com.wallet_service.application.ports.output.WalletRepository;
import com.wallet_service.adapters.output.client.SpringUserClient;
import com.wallet_service.adapters.output.client.UserClientImple;
import com.wallet_service.adapters.output.messaging.RabbitWalletEventPublisher;
import com.wallet_service.adapters.output.persistence.SpringJpaWalletRepository;
import com.wallet_service.adapters.output.persistence.WalletRepositoryImple;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public WalletEventPublisher walletEventPublisher(RabbitTemplate rabbitTemplate){
        return new RabbitWalletEventPublisher(rabbitTemplate);
    }

    @Bean
    public WalletRepository walletRepository(SpringJpaWalletRepository springJpaWalletRepository){
        return new WalletRepositoryImple(springJpaWalletRepository);
    }

    @Bean
    public UserClient userClient(SpringUserClient springUserClient) {
        return new UserClientImple(springUserClient);
    }
}
