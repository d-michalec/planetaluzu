package com.planetaluzu.ticket.domain;

import com.planetaluzu.common.exception.BusinessRuleException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Ticket {

    private Long id;
    private final Long registrationId;
    private final String ticketUuid;
    private TicketStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime checkedInAt;

    public Ticket(Long id,
                  Long registrationId,
                  String ticketUuid,
                  TicketStatus status,
                  LocalDateTime createdAt,
                  LocalDateTime checkedInAt) {
        this.id = id;
        this.registrationId = registrationId;
        this.ticketUuid = ticketUuid;
        this.status = status;
        this.createdAt = createdAt;
        this.checkedInAt = checkedInAt;
    }

    public static Ticket createForUser(Long userId) {
        return new Ticket(
                null,
                userId,
                java.util.UUID.randomUUID().toString(),
                TicketStatus.PAID,
                LocalDateTime.now(),
                null
        );
    }

    public void checkIn() {
        if (this.status != TicketStatus.PAID) {
            throw new BusinessRuleException(
                    "TICKET_NOT_PAID",
                    "Bilet musi być opłacony przed przypisaniem słuchawek."
            );
        }
        this.status = TicketStatus.CHECKED_IN;
        this.checkedInAt = LocalDateTime.now();
    }

    public void resetCheckIn() {
        this.status = TicketStatus.PAID;
        this.checkedInAt = null;
    }
}
