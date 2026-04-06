package com.planetaluzu.ticket.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Ticket {

    private Long id;
    private final Long userId;
    private final String ticketUuid;
    private TicketStatus status;
    private Long headphonesId;
    private final LocalDateTime createdAt;
    private LocalDateTime checkedInAt;

    public Ticket(Long userId) {
        this.userId = userId;
        this.ticketUuid = UUID.randomUUID().toString();
        this.status = TicketStatus.PAID;
        this.createdAt = LocalDateTime.now();
    }

    public void checkIn() {
        if (this.status == TicketStatus.CHECKED_IN) {
            throw new IllegalStateException("Ticket already checked in");
        }
        this.status = TicketStatus.CHECKED_IN;
        this.checkedInAt = LocalDateTime.now();
    }

    public void assignHeadphones(Long headphonesId) {
        this.headphonesId = headphonesId;
    }
}