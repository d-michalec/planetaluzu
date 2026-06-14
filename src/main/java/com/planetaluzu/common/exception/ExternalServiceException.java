package com.planetaluzu.common.exception;

import org.springframework.http.HttpStatus;

public class ExternalServiceException extends ApiException {
    public ExternalServiceException(String code, String message, Throwable cause) {
        super(HttpStatus.BAD_GATEWAY, code, message, cause);
    }
}
