package com.planetaluzu.headphonesAssignment.infrastructure.entity;

import com.planetaluzu.headphonesAssignment.domain.AssignmentStatus;
import com.planetaluzu.registration.infrastructure.entity.RegistrationJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "headphones_assignment")
@Getter
@Setter
public class HeadphonesAssignmentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "headphones_id", nullable = false)
    private Long headphonesId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", nullable = false, unique = true)
    private RegistrationJpaEntity registration;

    @Column(name="ticket_uuid", nullable = false)
    private String ticketUUID;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

}
