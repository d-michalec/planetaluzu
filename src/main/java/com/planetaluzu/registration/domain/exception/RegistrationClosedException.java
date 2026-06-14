package com.planetaluzu.registration.domain.exception;

import com.planetaluzu.common.exception.BusinessRuleException;

public class RegistrationClosedException extends BusinessRuleException {
    public RegistrationClosedException() {
        super("REGISTRATION_CLOSED", "Rejestracja na to wydarzenie jest już zamknięta.");
    }
}
