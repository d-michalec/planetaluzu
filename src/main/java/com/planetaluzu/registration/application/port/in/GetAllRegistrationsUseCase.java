package com.planetaluzu.registration.application.port.in;

import com.planetaluzu.registration.domain.Registration;

import java.util.List;

public interface GetAllRegistrationsUseCase {
    List<Registration> execute();
}
