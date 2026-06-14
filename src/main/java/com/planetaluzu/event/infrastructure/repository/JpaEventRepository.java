package com.planetaluzu.event.infrastructure.repository;

import com.planetaluzu.event.infrastructure.entity.EventJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEventRepository  extends JpaRepository<EventJpaEntity, Long> {
}
