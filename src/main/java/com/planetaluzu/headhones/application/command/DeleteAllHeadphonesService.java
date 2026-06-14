package com.planetaluzu.headhones.application.command;

import com.planetaluzu.headhones.application.port.DeleteAllHeadphonesUseCase;
import com.planetaluzu.headhones.port.HeadphonesRepository;
import com.planetaluzu.headphonesAssignment.application.port.DeleteAllHeadphonesAssignmentsUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAllHeadphonesService implements DeleteAllHeadphonesUseCase {

    private final DeleteAllHeadphonesAssignmentsUseCase deleteAllHeadphonesAssignmentsUseCase;
    private final HeadphonesRepository headphonesRepository;

    @Override
    @Transactional
    public void execute() {
        deleteAllHeadphonesAssignmentsUseCase.execute();
        headphonesRepository.deleteAll();
    }
}
