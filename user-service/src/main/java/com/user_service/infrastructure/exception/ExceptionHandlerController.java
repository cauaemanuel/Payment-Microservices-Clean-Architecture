package com.user_service.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;
import org.springframework.security.access.AccessDeniedException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce("", (a, b) -> a + "; " + b);
        ResponseError error = new ResponseError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                message.trim(),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseError> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        ResponseError error = new ResponseError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                "Recurso não encontrado.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseError> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        ResponseError error = new ResponseError(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage() != null ? ex.getMessage() : "Credenciais inválidas.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseError> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ResponseError error = new ResponseError(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "Acesso negado.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public ResponseEntity<ResponseError> handleResponseStatusException(org.springframework.web.server.ResponseStatusException ex, HttpServletRequest request) {
        ResponseError error = new ResponseError(
                ex.getStatusCode().value(),
                ex.getMessage(),
                ex.getReason() != null ? ex.getReason() : "Erro de status.",
                request.getRequestURI()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ResponseError error = new ResponseError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage() != null ? ex.getMessage() : "Argumento inválido.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleAllExceptions(Exception ex, HttpServletRequest request) {
        ResponseError error = new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Erro interno do servidor.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}