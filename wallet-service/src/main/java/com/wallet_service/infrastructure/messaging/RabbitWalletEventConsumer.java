package com.wallet_service.infrastructure.messaging;

import com.wallet_service.application.dto.TransactionMessageDto;
import com.wallet_service.application.ports.input.ProcessTransactionPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitWalletEventConsumer {

    private ProcessTransactionPort processTransactionPort;

    public RabbitWalletEventConsumer(ProcessTransactionPort processTransactionPort) {
        this.processTransactionPort = processTransactionPort;
    }

    @RabbitListener(queues = "wallet.transfer")
    public void processTransaction(TransactionMessageDto transactionMessageDto){
        log.info("Received transaction: {}", transactionMessageDto);
        processTransactionPort.processTransaction(transactionMessageDto);
    }
}
