package com.planetaluzu.headphonesAssignment.port;

import com.planetaluzu.headphonesAssignment.domain.HeadphonesAssignment;

import java.util.List;
import java.util.Optional;

public interface HeadphonesAssignmentRepository {
    HeadphonesAssignment save(HeadphonesAssignment headphonesAssignment);
    Optional<HeadphonesAssignment> findByHeadphonesId(Long headphonesId);
    List<HeadphonesAssignment> findAll();
    void deleteAll();
}
