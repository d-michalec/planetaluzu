package com.planetaluzu.user.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String reservationId;
    private boolean isPaid;  
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public User(String firstName, String lastName, String email, String reservationId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.reservationId = reservationId;
        this.isPaid = false;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusHours(12);
    }

    public void markAsPaid() {
        this.isPaid = true;
    }
}