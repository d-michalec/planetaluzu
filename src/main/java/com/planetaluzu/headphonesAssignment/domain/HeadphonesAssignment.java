package com.planetaluzu.headphonesAssignment.domain;

import com.planetaluzu.registration.domain.Registration;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HeadphonesAssignment {

    private Long id;
    private Long headphonesId;
    private Registration registration;
    private String ticketUUID;

    private LocalDateTime createdAt;
    private LocalDateTime assignedAt;
    private LocalDateTime returnedAt;

    private AssignmentStatus status;

    public HeadphonesAssignment(Long headphonesId, Registration registration, String ticketUUID) {
        this.headphonesId = headphonesId;
        this.registration = registration;
        this.ticketUUID = ticketUUID;
        this.assignedAt = LocalDateTime.now();
        this.status = AssignmentStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public void markReturned() {
        this.status = AssignmentStatus.RETURNED;
        this.returnedAt = LocalDateTime.now();
    }

    public void markLost() {
        this.status = AssignmentStatus.LOST;
        this.returnedAt = LocalDateTime.now();
    }

    public void markDamaged() {
        this.status = AssignmentStatus.DAMAGED;
        this.returnedAt = LocalDateTime.now();
    }
}