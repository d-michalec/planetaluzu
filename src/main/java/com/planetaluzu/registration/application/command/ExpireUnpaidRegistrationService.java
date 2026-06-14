package com.planetaluzu.registration.application.command;

import com.planetaluzu.registration.application.port.out.EmailSender;
import com.planetaluzu.registration.domain.Registration;
import com.planetaluzu.registration.port.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpireUnpaidRegistrationService {

    private static final int EXPIRATION_WARNING_HOURS = 12;

    private final RegistrationRepository userRepository;
    private final EmailSender emailSender;

    public void expireRegistration() {
        LocalDateTime now = LocalDateTime.now();

        sendExpirationWarnings(now);
        deleteExpiredRegistrations(now);
    }

    private void sendExpirationWarnings(LocalDateTime now) {
        LocalDateTime warningCutoff = now.plusHours(EXPIRATION_WARNING_HOURS);

        for (Registration registration : userRepository.findUnpaidReservationsExpiringBefore(now, warningCutoff)) {
            emailSender.sendReservationExpirationWarning(
                    registration.getEmail(),
                    registration.getFirstName(),
                    registration.getLastName(),
                    registration.getReservationId(),
                    registration.getExpiresAt()
            );

            registration.markExpirationReminderSent();
            userRepository.save(registration);
        }
    }

    private void deleteExpiredRegistrations(LocalDateTime now) {
        for (Registration registration : userRepository.findExpiredUnpaidRegistrations(now)) {
            emailSender.sendReservationExpired(
                    registration.getEmail(),
                    registration.getFirstName(),
                    registration.getLastName(),
                    registration.getReservationId()
            );

            userRepository.deleteById(registration.getId());
        }
    }
}
