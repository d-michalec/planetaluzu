package com.planetaluzu.user.infrastructure.mapper;

import com.planetaluzu.user.domain.User;
import com.planetaluzu.user.infrastructure.entity.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toDomain(UserJpaEntity entity);
    UserJpaEntity toEntity(User user);
}