package com.planetaluzu.headphonesAssignment.application.command;

import com.planetaluzu.common.exception.BusinessRuleException;
import com.planetaluzu.common.exception.ConflictException;
import com.planetaluzu.common.exception.ResourceNotFoundException;
import com.planetaluzu.headhones.domain.Headphones;
import com.planetaluzu.headhones.port.HeadphonesRepository;
import com.planetaluzu.headphonesAssignment.application.port.AssignHeadphonesToUserUseCase;
import com.planetaluzu.headphonesAssignment.domain.HeadphonesAssignment;
import com.planetaluzu.headphonesAssignment.port.HeadphonesAssignmentRepository;
import com.planetaluzu.registration.domain.Registration;
import com.planetaluzu.registration.port.RegistrationRepository;
import com.planetaluzu.ticket.domain.Ticket;
import com.planetaluzu.ticket.domain.TicketStatus;
import com.planetaluzu.ticket.port.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignHeadphonesToUserService implements AssignHeadphonesToUserUseCase {

    private final TicketRepository ticketRepository;
    private final HeadphonesRepository headphonesRepository;
    private final RegistrationRepository userRepository;
    private final HeadphonesAssignmentRepository headphonesAssignmentRepository;

    @Transactional
    @Override
    public HeadphonesAssignment execute(String ticketUUID, Long headphonesId) {
        Ticket ticket = ticketRepository.findByTicketUuid(ticketUUID)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "TICKET_NOT_FOUND",
                        "Nie znaleziono biletu."
                ));

        Registration registration = userRepository.findById(ticket.getRegistrationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "REGISTRATION_NOT_FOUND",
                        "Nie znaleziono rezerwacji przypisanej do biletu."
                ));

        return assignHeadphones(ticket, registration, headphonesId);
    }

    @Transactional
    @Override
    public HeadphonesAssignment executeByEmail(String email, Long headphonesId) {
        Registration registration = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "REGISTRATION_NOT_FOUND",
                        "Nie znaleziono rezerwacji dla podanego adresu e-mail."
                ));

        Ticket ticket = ticketRepository.findByRegistrationId(registration.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "TICKET_NOT_FOUND",
                        "Ta rezerwacja nie ma jeszcze potwierdzonego biletu."
                ));

        return assignHeadphones(ticket, registration, headphonesId);
    }

    private HeadphonesAssignment assignHeadphones(Ticket ticket, Registration registration, Long headphonesId) {
        if (ticket.getStatus() == TicketStatus.CHECKED_IN) {
            throw new ConflictException(
                    "HEADPHONES_ALREADY_ASSIGNED",
                    "Słuchawki zostały już przypisane do tego biletu."
            );
        }

        Headphones headphones = findOrCreateHeadphones(headphonesId);

        if (!headphones.isAvailable()) {
            throw new BusinessRuleException(
                    "HEADPHONES_NOT_AVAILABLE",
                    "Te słuchawki nie są dostępne."
            );
        }

        HeadphonesAssignment headphonesAssignment = new HeadphonesAssignment(
                headphones.getHeadphonesId(),
                registration,
                ticket.getTicketUuid()
        );

        ticket.checkIn();
        headphones.markAsIssued();

        headphonesRepository.save(headphones);
        ticketRepository.save(ticket);
        return headphonesAssignmentRepository.save(headphonesAssignment);
    }

    private Headphones findOrCreateHeadphones(Long headphonesId) {
        return headphonesRepository.findByHeadphonesId(headphonesId)
                .orElseGet(() -> {
                    Headphones headphones = new Headphones(headphonesId);
                    headphones.markAsAvailable();
                    return headphonesRepository.save(headphones);
                });
    }
}
