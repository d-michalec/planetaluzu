package com.planetaluzu.registration.domain.exception;

import com.planetaluzu.common.exception.ConflictException;

public class RegistrationAlreadyExistsException extends ConflictException {
    public RegistrationAlreadyExistsException(String email) {
        super("REGISTRATION_ALREADY_EXISTS", "Ten adres e-mail jest już zarejestrowany.");
    }
}
