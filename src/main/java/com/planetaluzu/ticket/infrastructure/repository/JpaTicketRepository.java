package com.planetaluzu.ticket.infrastructure.repository;

import com.planetaluzu.ticket.infrastructure.entity.TicketJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTicketRepository extends JpaRepository<TicketJpaEntity, Long> {
}
