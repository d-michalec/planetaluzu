package com.planetaluzu.headhones.infrastructure.mapper;

import com.planetaluzu.headhones.domain.Headphones;
import com.planetaluzu.headhones.infrastructure.entity.HeadphonesJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HeadphonesMapper {
    HeadphonesMapper INSTANCE = Mappers.getMapper(HeadphonesMapper.class);
    Headphones toDomain(HeadphonesJpaEntity entity);
    HeadphonesJpaEntity toEntity(Headphones headphones);
}
