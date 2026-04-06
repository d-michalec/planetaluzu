package com.planetaluzu.ticket.port;

import com.planetaluzu.ticket.domain.Ticket;
import com.planetaluzu.ticket.infrastructure.entity.TicketJpaEntity;
import com.planetaluzu.ticket.infrastructure.mapper.TicketMapper;
import com.planetaluzu.ticket.infrastructure.repository.JpaTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}