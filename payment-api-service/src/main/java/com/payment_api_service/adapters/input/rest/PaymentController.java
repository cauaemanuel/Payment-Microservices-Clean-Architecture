package com.payment_api_service.adapters.input.rest;

import com.payment_api_service.application.ports.input.InitiateTransferPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Pagamentos", description = "Operações de pagamento")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private InitiateTransferPort initiateTransferUseCase;

    public PaymentController(InitiateTransferPort initiateTransferUseCase) {
        this.initiateTransferUseCase = initiateTransferUseCase;
    }

    @Operation(
            summary = "Realiza transferência de pagamento",
            description = "Transfere um valor de uma conta de origem para uma conta de destino.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso")
            }
    )
    @PostMapping("/transfer")
    public ResponseEntity transfer(
            @Parameter(description = "ID do destinatário", required = true)
            @RequestParam String destinationEmail,
            @RequestHeader("Authorization") String authorization,
            @Parameter(description = "Valor a ser transferido", required = true)
            @RequestParam double amount) {
        String token = extractToken(authorization);
        initiateTransferUseCase.execute(destinationEmail, token, amount);
        return ResponseEntity.ok("Payment transferred successfully");
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return authorizationHeader;
    }
}
