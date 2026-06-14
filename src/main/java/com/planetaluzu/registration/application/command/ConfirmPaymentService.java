package com.planetaluzu.registration.application.command;

import com.planetaluzu.common.exception.BusinessRuleException;
import com.planetaluzu.common.exception.ResourceNotFoundException;
import com.planetaluzu.ticket.domain.Ticket;
import com.planetaluzu.ticket.port.TicketRepository;
import com.planetaluzu.registration.application.port.in.ConfirmPaymentUseCase;
import com.planetaluzu.registration.application.port.out.EmailSender;
import com.planetaluzu.registration.domain.Registration;
import com.planetaluzu.registration.port.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ConfirmPaymentService implements ConfirmPaymentUseCase {

    private final RegistrationRepository userRepository;
    private final EmailSender emailSender;
    private final TicketRepository ticketRepository;

    @Override
    @Transactional
    public Ticket execute(String reservationId) {
        Registration registration = userRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "REGISTRATION_NOT_FOUND",
                        "Nie znaleziono rezerwacji."
                ));

        if (ticketRepository.findByRegistrationId(registration.getId()).isPresent()) {
            throw new BusinessRuleException(
                    "TICKET_ALREADY_EXISTS",
                    "Bilet dla tej rezerwacji został już utworzony."
            );
        }

        registration.markAsPaid();

        Ticket ticket = Ticket.createForUser(registration.getId());

        Ticket savedTicket = ticketRepository.save(ticket);

        emailSender.sendReservationConfirmation(
                registration.getEmail(),
                registration.getFirstName(),
                registration.getLastName(),
                ticket.getTicketUuid()
        );

        userRepository.save(registration);

        return savedTicket;
    }
}
