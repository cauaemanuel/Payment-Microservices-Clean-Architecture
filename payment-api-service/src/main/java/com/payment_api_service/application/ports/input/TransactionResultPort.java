package com.payment_api_service.application.ports.input;

import com.payment_api_service.application.dto.TransactionMessageDto;

public interface TransactionResultPort {
    void processAcceptedTransaction(TransactionMessageDto dto);
    void processRejectedTransaction(TransactionMessageDto dto);
}
