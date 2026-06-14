package com.planetaluzu.ticket.infrastructure.repository;

import com.planetaluzu.ticket.infrastructure.entity.TicketJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTicketRepository extends JpaRepository<TicketJpaEntity, Long> {
    Optional<TicketJpaEntity> findByTicketUuid(String reservationId);
    Optional<TicketJpaEntity> findByRegistrationId(Long registrationId);
}
