package com.planetaluzu.registration.application.command;

import com.planetaluzu.common.exception.BusinessRuleException;
import com.planetaluzu.event.application.port.GetCurrentEventUseCase;
import com.planetaluzu.event.domain.Event;
import com.planetaluzu.registration.application.port.in.RegisterUseCase;
import com.planetaluzu.registration.application.port.out.EmailSender;
import com.planetaluzu.registration.domain.Registration;
import com.planetaluzu.registration.domain.exception.RegistrationAlreadyExistsException;
import com.planetaluzu.registration.domain.exception.RegistrationClosedException;
import com.planetaluzu.registration.port.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


import static com.planetaluzu.registration.application.util.ReservationNumberGenerator.generateReservationNumber;

@Service
@RequiredArgsConstructor
public class RegisterService implements RegisterUseCase {
    private static final int REGISTRATION_CLOSE_DAYS_BEFORE_EVENT = 7;

    private final RegistrationRepository userRepository;
    private final EmailSender emailSender;
    private final GetCurrentEventUseCase getCurrentEventUseCase;

    @Override
    public Registration execute(String firstName, String lastName, String email) {
        validateRegistrationWindow();
        return register(firstName, lastName, email);
    }

    @Override
    public Registration executeByAdmin(String firstName, String lastName, String email) {
        return register(firstName, lastName, email);
    }

    private Registration register(String firstName, String lastName, String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RegistrationAlreadyExistsException(email);
        }

        Event currentEvent = getCurrentEventUseCase.execute()
                .orElseThrow(RegistrationClosedException::new);
        if (currentEvent.getPrice() == null || currentEvent.getPrice().signum() <= 0) {
            throw new BusinessRuleException(
                    "EVENT_PRICE_NOT_SET",
                    "Cena aktualnego eventu nie zostala ustawiona."
            );
        }
        String reservationId = generateReservationNumber(firstName, lastName);
        Registration registration = new Registration(firstName, lastName, email, reservationId);
        Registration savedRegistration = userRepository.save(registration);

        emailSender.generateReservationEmail(
                savedRegistration.getEmail(),
                savedRegistration.getFirstName(),
                savedRegistration.getLastName(),
                savedRegistration.getReservationId(),
                savedRegistration.getExpiresAt(),
                currentEvent.getPrice()
        );

        return savedRegistration;
    }

    private void validateRegistrationWindow() {
        LocalDateTime now = LocalDateTime.now();

        boolean registrationOpen = getCurrentEventUseCase.execute()
                .map(event -> now.isBefore(event.getDate().minusDays(REGISTRATION_CLOSE_DAYS_BEFORE_EVENT)))
                .orElse(false);

        if (!registrationOpen) {
            throw new RegistrationClosedException();
        }
    }
}
