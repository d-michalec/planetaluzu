package com.planetaluzu.headphonesAssignment.application.query;

import com.planetaluzu.auth.web.dto.Attendee;
import com.planetaluzu.headphonesAssignment.application.port.GetHeadphonesAssignmentUseCase;
import com.planetaluzu.headphonesAssignment.port.HeadphonesAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetHeadphonesAssignmentService implements GetHeadphonesAssignmentUseCase {

    private final HeadphonesAssignmentRepository repository;

    @Override
    public List<Attendee> execute() {

        return repository.findAll()
                .stream()
                .map(a -> {
                    Attendee dto = new Attendee();

                    dto.setFirstName(a.getRegistration().getFirstName());
                    dto.setLastName(a.getRegistration().getLastName());
                    dto.setHeadphonesId(a.getHeadphonesId());
                    dto.setAssignmentStatus(a.getStatus());

                    return dto;
                })
                .toList();
    }
}