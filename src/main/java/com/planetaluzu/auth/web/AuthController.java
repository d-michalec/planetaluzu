package com.planetaluzu.auth.web;

import com.planetaluzu.auth.web.dto.RegisterRequest;
import com.planetaluzu.auth.web.dto.RegisterResponse;
import com.planetaluzu.user.application.port.in.RegisterUserUseCase;
import com.planetaluzu.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        User user = registerUserUseCase.execute(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );

        return new RegisterResponse(user.getId(), user.getEmail(), user.getReservationId());
    }
}