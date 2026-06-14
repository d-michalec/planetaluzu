package com.planetaluzu.registration.application.port.in;

import com.planetaluzu.ticket.domain.Ticket;

public interface ConfirmPaymentUseCase {
    Ticket execute(String reservationId);
}
