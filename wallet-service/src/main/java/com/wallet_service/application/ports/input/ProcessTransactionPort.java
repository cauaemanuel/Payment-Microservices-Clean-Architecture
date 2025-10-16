package com.wallet_service.application.ports.input;

import com.wallet_service.application.dto.TransactionMessageDto;

public interface ProcessTransactionPort {
    void processTransaction(TransactionMessageDto dto);
}
