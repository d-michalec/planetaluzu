package com.planetaluzu.headphonesAssignment.port;

import com.planetaluzu.headphonesAssignment.domain.HeadphonesAssignment;
import com.planetaluzu.headphonesAssignment.infrastructure.entity.HeadphonesAssignmentJpaEntity;
import com.planetaluzu.headphonesAssignment.infrastructure.mapper.HeadphonesAssignmentMapper;
import com.planetaluzu.headphonesAssignment.infrastructure.repository.JpaHeadphonesAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class HeadphonesAssignmentRepositoryImpl implements HeadphonesAssignmentRepository {
    private final JpaHeadphonesAssignmentRepository jpaHeadphonesAssignmentRepository;
    private final HeadphonesAssignmentMapper headphonesAssignmentMapper;
    @Override
    public HeadphonesAssignment save(HeadphonesAssignment headphonesAssignment) {
        HeadphonesAssignmentJpaEntity entity = headphonesAssignmentMapper.toEntity(headphonesAssignment);
        HeadphonesAssignmentJpaEntity saved = jpaHeadphonesAssignmentRepository.save(entity);
        return headphonesAssignmentMapper.toDomain(saved);
    }

    @Override
    public Optional<HeadphonesAssignment> findByHeadphonesId(Long headphonesId) {
        return jpaHeadphonesAssignmentRepository
                .findByHeadphonesId(headphonesId)
                .map(headphonesAssignmentMapper::toDomain);
    }

    @Override
    public List<HeadphonesAssignment> findAll() {
        return jpaHeadphonesAssignmentRepository.
                findAllWithRegistration()
                .stream()
                .map(headphonesAssignmentMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteAll() {
        jpaHeadphonesAssignmentRepository.deleteAllInBatch();
    }
}
