package com.planetaluzu.auth.web.dto;


public record RegisterResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String reservationId
) {}