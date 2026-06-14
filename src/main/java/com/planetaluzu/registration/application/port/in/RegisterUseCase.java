package com.planetaluzu.registration.application.port.in;

import com.planetaluzu.registration.domain.Registration;

public interface RegisterUseCase {
    Registration execute(String firstName, String lastName, String email);

    Registration executeByAdmin(String firstName, String lastName, String email);
}
