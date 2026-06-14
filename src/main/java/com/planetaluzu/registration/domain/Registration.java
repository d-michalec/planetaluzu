package com.planetaluzu.registration.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Registration {

    private static final int RESERVATION_EXPIRATION_HOURS = 24;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String reservationId;
    private boolean isPaid;  
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Boolean expirationReminderSent;

    public Registration(String firstName, String lastName, String email, String reservationId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.reservationId = reservationId;
        this.isPaid = false;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusHours(RESERVATION_EXPIRATION_HOURS);
        this.expirationReminderSent = false;
    }

    public void markAsPaid() {
        this.isPaid = true;
    }

    public void markExpirationReminderSent() {
        this.expirationReminderSent = true;
    }
}
