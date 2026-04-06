package com.planetaluzu.ticket.infrastructure.mapper;

import com.planetaluzu.ticket.domain.Ticket;
import com.planetaluzu.ticket.infrastructure.entity.TicketJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);
    Ticket toDomain(TicketJpaEntity entity);
    TicketJpaEntity toEntity(Ticket ticket);
}
