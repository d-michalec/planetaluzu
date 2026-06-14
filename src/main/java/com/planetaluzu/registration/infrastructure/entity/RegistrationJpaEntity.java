package com.planetaluzu.registration.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "registration ")
@Getter
@Setter
public class RegistrationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "reservation_id", nullable = false, unique = true)
    private String reservationId;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "expiration_reminder_sent")
    private Boolean expirationReminderSent = false;
}
