package com.planetaluzu.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessRuleException extends ApiException {
    public BusinessRuleException(String code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }
}
