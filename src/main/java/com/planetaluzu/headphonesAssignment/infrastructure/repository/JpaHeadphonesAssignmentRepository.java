package com.planetaluzu.headphonesAssignment.infrastructure.repository;

import com.planetaluzu.headphonesAssignment.infrastructure.entity.HeadphonesAssignmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaHeadphonesAssignmentRepository extends JpaRepository<HeadphonesAssignmentJpaEntity, Long> {
    @Query("""
    select a
    from HeadphonesAssignmentJpaEntity a
    where a.headphonesId = :headphonesId
      and a.status = 'ACTIVE'
""")
    Optional<HeadphonesAssignmentJpaEntity> findActiveByHeadphonesId(@Param("headphonesId") Long headphonesId);

    @Query("""
    SELECT h FROM HeadphonesAssignmentJpaEntity h
    JOIN FETCH h.registration
""")
    List<HeadphonesAssignmentJpaEntity> findAllWithRegistration();

    Optional<HeadphonesAssignmentJpaEntity> findByHeadphonesId(Long headphonesId);
}
