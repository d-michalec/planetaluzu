package com.planetaluzu.ticket.port;

import com.planetaluzu.ticket.domain.Ticket;

import java.util.Optional;

public interface TicketRepository {
    Ticket save(Ticket ticket);
    Optional<Ticket> findByTicketUuid(String reservationId);
    Optional<Ticket> findByRegistrationId(Long registrationId);
    void deleteAll();
}
