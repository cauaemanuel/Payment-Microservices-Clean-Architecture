package com.payment_api_service.infrastructure.config;

import com.payment_api_service.application.ports.output.WalletClient;
import com.payment_api_service.application.ports.output.PaymentEventPublisher;
import com.payment_api_service.application.ports.output.TransactionRepository;
import com.payment_api_service.adapters.output.client.SpringUserClient;
import com.payment_api_service.adapters.output.client.SpringWalletClient;
import com.payment_api_service.adapters.output.client.UserClientImple;
import com.payment_api_service.adapters.output.client.WalletClientImple;
import com.payment_api_service.adapters.output.messaging.RabbitPaymentEventPublisher;
import com.payment_api_service.adapters.output.persistence.SpringDataTransactionRepository;
import com.payment_api_service.adapters.output.persistence.TransactionRepositoryImple;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public TransactionRepository transactionRepository(SpringDataTransactionRepository springDataTransactionRepository) {
        return new TransactionRepositoryImple(springDataTransactionRepository);
    }

    @Bean
    public WalletClient walletClient(SpringWalletClient springWalletClient){
        return new WalletClientImple(springWalletClient);
    }

    @Bean
    public PaymentEventPublisher paymentEventPublisher(RabbitTemplate rabbitTemplate) {
        return new RabbitPaymentEventPublisher(rabbitTemplate);
    }

    @Bean
    public com.payment_api_service.application.ports.output.UserClient userClient(SpringUserClient springUserClient) {
        return new UserClientImple(springUserClient);
    }

}
