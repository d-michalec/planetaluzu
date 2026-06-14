package com.planetaluzu.headphonesAssignment.application.command;

import com.planetaluzu.common.exception.ResourceNotFoundException;
import com.planetaluzu.headhones.domain.Headphones;
import com.planetaluzu.headhones.domain.HeadphonesStatus;
import com.planetaluzu.headhones.port.HeadphonesRepository;
import com.planetaluzu.headphonesAssignment.application.port.UserReturnHeadphonesUseCase;
import com.planetaluzu.headphonesAssignment.domain.AssignmentStatus;
import com.planetaluzu.headphonesAssignment.domain.HeadphonesAssignment;
import com.planetaluzu.headphonesAssignment.domain.ReturnCondition;
import com.planetaluzu.headphonesAssignment.port.HeadphonesAssignmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserReturnHeadphonesService implements UserReturnHeadphonesUseCase {

    private final HeadphonesAssignmentRepository headphonesAssignmentRepository;
    private final HeadphonesRepository headphonesRepository;

    @Transactional
    public HeadphonesAssignment execute(Long headphonesId, ReturnCondition condition) {

        HeadphonesAssignment assignment = headphonesAssignmentRepository
                .findByHeadphonesId(headphonesId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "HEADPHONES_ASSIGNMENT_NOT_FOUND",
                        "Nie znaleziono aktywnego przypisania dla słuchawek o ID " + headphonesId + "."
                ));

        assignment.setReturnedAt(LocalDateTime.now());

        Headphones headphones = headphonesRepository
                .findByHeadphonesId(headphonesId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "HEADPHONES_NOT_FOUND",
                        "Nie znaleziono słuchawek o ID " + headphonesId + "."
                ));

        switch (condition) {
            case OK -> {
                assignment.setStatus(AssignmentStatus.RETURNED);
                headphones.setStatus(HeadphonesStatus.DOSTĘPNE);
            }
            case DAMAGED -> {
                assignment.setStatus(AssignmentStatus.DAMAGED);
                headphones.setStatus(HeadphonesStatus.ZEPSUTE);
            }
            case LOST -> {
                assignment.setStatus(AssignmentStatus.LOST);
                headphones.setStatus(HeadphonesStatus.ZGUBIONE);
            }
        }

        headphonesAssignmentRepository.save(assignment);
        headphonesRepository.save(headphones);

        return assignment;
    }
}
