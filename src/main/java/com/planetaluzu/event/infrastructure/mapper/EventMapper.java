package com.planetaluzu.event.infrastructure.mapper;

import com.planetaluzu.event.domain.Event;
import com.planetaluzu.event.infrastructure.entity.EventJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
    Event toDomain(EventJpaEntity entity);
    EventJpaEntity toEntity(Event event);
}
