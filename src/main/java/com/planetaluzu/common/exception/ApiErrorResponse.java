package com.planetaluzu.common.exception;

import java.time.Instant;

public record ApiErrorResponse(
        String code,
        String message,
        int status,
        Instant timestamp
) {
    public static ApiErrorResponse of(String code, String message, int status) {
        return new ApiErrorResponse(code, message, status, Instant.now());
    }
}
