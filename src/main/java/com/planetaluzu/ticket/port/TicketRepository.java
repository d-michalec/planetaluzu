package com.planetaluzu.ticket.port;

import com.planetaluzu.ticket.domain.Ticket;

public interface TicketRepository {
    Ticket save(Ticket ticket);

}
