package com.planetaluzu.registration.infrastructure.mapper;

import com.planetaluzu.registration.domain.Registration;
import com.planetaluzu.registration.infrastructure.entity.RegistrationJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    Registration toDomain(RegistrationJpaEntity entity);
    RegistrationJpaEntity toEntity(Registration registration);
}