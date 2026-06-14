package com.planetaluzu.headphonesAssignment.infrastructure.mapper;

import com.planetaluzu.headphonesAssignment.domain.HeadphonesAssignment;
import com.planetaluzu.headphonesAssignment.infrastructure.entity.HeadphonesAssignmentJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HeadphonesAssignmentMapper {
    HeadphonesAssignment toDomain(HeadphonesAssignmentJpaEntity entity);
    HeadphonesAssignmentJpaEntity toEntity(HeadphonesAssignment headphonesAssignment);
}

