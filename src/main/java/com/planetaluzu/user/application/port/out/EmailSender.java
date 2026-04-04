package com.planetaluzu.user.application.port.out;

import java.time.LocalDateTime;

public interface EmailSender {

    void generateReservationEmail(
            String email, String firstName, String lastName, String reservationId, LocalDateTime expiresAt);

    void sendReservationConfirmation(String email, String firstName, String lastName, String qrToken);
}