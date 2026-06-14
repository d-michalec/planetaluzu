package com.planetaluzu.auth.web;

import com.planetaluzu.auth.web.dto.RegisterRequest;
import com.planetaluzu.registration.application.port.in.ConfirmPaymentUseCase;
import com.planetaluzu.registration.application.port.in.DeleteAllRegistrationsUseCase;
import com.planetaluzu.registration.application.port.in.GetAllRegistrationsUseCase;
import com.planetaluzu.registration.application.port.in.RegisterUseCase;
import com.planetaluzu.registration.domain.Registration;
import com.planetaluzu.ticket.domain.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/admin/registration")
@RequiredArgsConstructor
public class AdminRegistrationController {

    private final ConfirmPaymentUseCase confirmPaymentUseCase;
    private final GetAllRegistrationsUseCase getAllRegistrationsUseCase;
    private final RegisterUseCase registerUseCase;
    private final DeleteAllRegistrationsUseCase deleteAllRegistrationsUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Registration registerByAdmin(@RequestBody RegisterRequest request) {
        return registerUseCase.executeByAdmin(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );
    }

    @PostMapping("/{reservationId}/confirm-payment")
    @ResponseStatus(HttpStatus.OK)
    public Ticket confirmPayment(@PathVariable String reservationId) {
        return confirmPaymentUseCase.execute(reservationId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Registration> findAll() {
        return getAllRegistrationsUseCase.execute();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        deleteAllRegistrationsUseCase.execute();
    }
}
