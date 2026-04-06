package com.planetaluzu.user.port;

import com.planetaluzu.user.domain.User;
import com.planetaluzu.user.infrastructure.entity.UserJpaEntity;
import com.planetaluzu.user.infrastructure.mapper.UserMapper;
import com.planetaluzu.user.infrastructure.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserJpaEntity entity = userMapper.toEntity(user);
        UserJpaEntity saved = jpaUserRepository.save(entity);
        return userMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByReservationId(String reservationId) {
        return jpaUserRepository.findByReservationId(reservationId)
                .map(userMapper::toDomain);
    }
}