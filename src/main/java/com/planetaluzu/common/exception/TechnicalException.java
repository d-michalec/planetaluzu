package com.planetaluzu.common.exception;

import org.springframework.http.HttpStatus;

public class TechnicalException extends ApiException {
    public TechnicalException(String code, String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, code, message, cause);
    }
}
