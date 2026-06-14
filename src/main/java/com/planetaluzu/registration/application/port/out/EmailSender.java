package com.planetaluzu.registration.application.port.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface EmailSender {

    void generateReservationEmail(
            String email,
            String firstName,
            String lastName,
            String reservationId,
            LocalDateTime expiresAt,
            BigDecimal amount);

    void sendReservationConfirmation(String email, String firstName, String lastName, String qrToken);

    void sendReservationExpirationWarning(
            String email, String firstName, String lastName, String reservationId, LocalDateTime expiresAt);

    void sendReservationExpired(String email, String firstName, String lastName, String reservationId);
}
