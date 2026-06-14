package com.planetaluzu.headphonesAssignment.application.command;

import com.planetaluzu.headhones.domain.Headphones;
import com.planetaluzu.headhones.port.HeadphonesRepository;
import com.planetaluzu.headphonesAssignment.application.port.DeleteAllHeadphonesAssignmentsUseCase;
import com.planetaluzu.headphonesAssignment.domain.AssignmentStatus;
import com.planetaluzu.headphonesAssignment.domain.HeadphonesAssignment;
import com.planetaluzu.headphonesAssignment.port.HeadphonesAssignmentRepository;
import com.planetaluzu.ticket.domain.Ticket;
import com.planetaluzu.ticket.port.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAllHeadphonesAssignmentsService implements DeleteAllHeadphonesAssignmentsUseCase {

    private final HeadphonesAssignmentRepository headphonesAssignmentRepository;
    private final HeadphonesRepository headphonesRepository;
    private final TicketRepository ticketRepository;

    @Override
    @Transactional
    public void execute() {
        for (HeadphonesAssignment assignment : headphonesAssignmentRepository.findAll()) {
            ticketRepository.findByTicketUuid(assignment.getTicketUUID()).ifPresent(ticket -> {
                ticket.resetCheckIn();
                ticketRepository.save(ticket);
            });

            if (assignment.getStatus() == AssignmentStatus.ACTIVE) {
                headphonesRepository.findByHeadphonesId(assignment.getHeadphonesId()).ifPresent(headphones -> {
                    headphones.markAsAvailable();
                    headphonesRepository.save(headphones);
                });
            }
        }

        headphonesAssignmentRepository.deleteAll();
    }
}
