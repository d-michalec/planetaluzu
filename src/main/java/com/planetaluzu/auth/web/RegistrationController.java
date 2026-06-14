package com.planetaluzu.auth.web;

import com.planetaluzu.auth.web.dto.RegisterRequest;
import com.planetaluzu.auth.web.dto.RegisterResponse;
import com.planetaluzu.registration.application.port.in.RegisterUseCase;
import com.planetaluzu.registration.domain.Registration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegisterUseCase registerUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        Registration registration = registerUseCase.execute(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );

        return new RegisterResponse(
                registration.getId(),
                registration.getFirstName(),
                registration.getLastName(),
                registration.getEmail(),
                registration.getReservationId()
        );
    }
}