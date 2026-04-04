package com.planetaluzu.auth.web.dto;


public record RegisterResponse(
        Long id,
        String email,
        String reservationId
) {}