package com.planetaluzu.user.application.port.in;

import com.planetaluzu.ticket.domain.Ticket;

public interface ConfirmPaymentUseCase {
    Ticket execute(String reservationId);
}
