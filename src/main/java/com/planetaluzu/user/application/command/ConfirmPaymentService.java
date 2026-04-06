package com.planetaluzu.user.application.command;

import com.planetaluzu.ticket.domain.Ticket;
import com.planetaluzu.ticket.port.TicketRepository;
import com.planetaluzu.user.application.port.in.ConfirmPaymentUseCase;
import com.planetaluzu.user.application.port.out.EmailSender;
import com.planetaluzu.user.domain.User;
import com.planetaluzu.user.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ConfirmPaymentService implements ConfirmPaymentUseCase {

    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final TicketRepository ticketRepository;

    @Override
    @Transactional
    public Ticket execute(String reservationId) {
        User user = userRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.markAsPaid();

        Ticket ticket = createTicketForUser(user);

        Ticket savedTicket = ticketRepository.save(ticket);

        emailSender.sendReservationConfirmation(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                ticket.getTicketUuid()
        );

        userRepository.save(user);

        return savedTicket;
    }
    private Ticket createTicketForUser(User user) {
        return new Ticket(user.getId());
    }
}
