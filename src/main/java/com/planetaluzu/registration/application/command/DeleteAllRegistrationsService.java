package com.planetaluzu.registration.application.command;

import com.planetaluzu.headphonesAssignment.application.port.DeleteAllHeadphonesAssignmentsUseCase;
import com.planetaluzu.registration.application.port.in.DeleteAllRegistrationsUseCase;
import com.planetaluzu.registration.port.RegistrationRepository;
import com.planetaluzu.ticket.port.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAllRegistrationsService implements DeleteAllRegistrationsUseCase {

    private final DeleteAllHeadphonesAssignmentsUseCase deleteAllHeadphonesAssignmentsUseCase;
    private final TicketRepository ticketRepository;
    private final RegistrationRepository registrationRepository;

    @Override
    @Transactional
    public void execute() {
        deleteAllHeadphonesAssignmentsUseCase.execute();
        ticketRepository.deleteAll();
        registrationRepository.deleteAll();
    }
}
