package com.planetaluzu.registration.application.query;

import com.planetaluzu.registration.application.port.in.GetAllRegistrationsUseCase;
import com.planetaluzu.registration.domain.Registration;
import com.planetaluzu.registration.port.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetRegistrationsQuery implements GetAllRegistrationsUseCase {
    private final RegistrationRepository registrationRepository;
    @Override
    public List<Registration> execute() {
        return registrationRepository.findAll();
    }
}
