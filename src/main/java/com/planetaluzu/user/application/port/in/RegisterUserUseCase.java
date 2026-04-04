package com.planetaluzu.user.application.port.in;

import com.planetaluzu.user.domain.User;

public interface RegisterUserUseCase {
    /**
     * Rejestruje nowego użytkownika.
     * @param firstName imię
     * @param lastName nazwisko
     * @param email email
     * @return zarejestrowanego użytkownika
//     * @throws UserAlreadyExistsException jeśli email już istnieje
     */
    User execute(String firstName, String lastName, String email) throws RuntimeException;
}