package com.planetaluzu.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ApiErrorResponse.of(e.getCode(), e.getMessage(), e.getStatus().value()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorResponse.of("INVALID_REQUEST", e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthentication(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiErrorResponse.of(
                        "INVALID_ADMIN_CREDENTIALS",
                        "Nieprawidłowy e-mail lub hasło.",
                        HttpStatus.UNAUTHORIZED.value()
                ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntime(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiErrorResponse.of(
                        "INTERNAL_SERVER_ERROR",
                        "Wystąpił błąd serwera. Spróbuj ponownie za chwilę.",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }
}
