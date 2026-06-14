package com.planetaluzu.ticket.port;

import com.planetaluzu.ticket.domain.Ticket;
import com.planetaluzu.ticket.infrastructure.entity.TicketJpaEntity;
import com.planetaluzu.ticket.infrastructure.mapper.TicketMapper;
import com.planetaluzu.ticket.infrastructure.repository.JpaTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TicketRepositoryImpl implements TicketRepository {
    private final TicketMapper ticketMapper;
    private final JpaTicketRepository jpaTicketRepository;

    @Override
    public Ticket save(Ticket ticket) {
        TicketJpaEntity entity = ticketMapper.toEntity(ticket);
        TicketJpaEntity saved = jpaTicketRepository.save(entity);
        return ticketMapper.toDomain(saved);
    }

    @Override
    public Optional<Ticket> findByTicketUuid(String reservationId) {
        return jpaTicketRepository.findByTicketUuid(reservationId).map(ticketMapper::toDomain);
    }

    @Override
    public Optional<Ticket> findByRegistrationId(Long registrationId) {
        return jpaTicketRepository.findByRegistrationId(registrationId).map(ticketMapper::toDomain);
    }

    @Override
    public void deleteAll() {
        jpaTicketRepository.deleteAllInBatch();
    }
}
