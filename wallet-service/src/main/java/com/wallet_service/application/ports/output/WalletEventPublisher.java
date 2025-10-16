package com.wallet_service.application.ports.output;

import com.wallet_service.application.dto.TransactionMessageDto;

public interface WalletEventPublisher {

    void processSucessfulPayment(TransactionMessageDto dto);
}
