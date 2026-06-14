package com.planetaluzu.headhones.infrastructure.repository;

import com.planetaluzu.headhones.infrastructure.entity.HeadphonesJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaHeadphonesRepository extends JpaRepository<HeadphonesJpaEntity, Long> {
    Optional<HeadphonesJpaEntity> findByHeadphonesId(Long headphonesId);
    void deleteByHeadphonesId(Long headphonesId);
}
