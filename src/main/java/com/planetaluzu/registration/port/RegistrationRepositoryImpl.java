package com.planetaluzu.registration.port;

import com.planetaluzu.registration.domain.Registration;
import com.planetaluzu.registration.infrastructure.entity.RegistrationJpaEntity;
import com.planetaluzu.registration.infrastructure.mapper.RegistrationMapper;
import com.planetaluzu.registration.infrastructure.repository.JpaRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RegistrationRepositoryImpl implements RegistrationRepository {

    private final JpaRegistrationRepository jpaRegistrationRepository;
    private final RegistrationMapper registrationMapper;

    @Override
    public Registration save(Registration registration) {
        RegistrationJpaEntity entity = registrationMapper.toEntity(registration);
        RegistrationJpaEntity saved = jpaRegistrationRepository.save(entity);
        return registrationMapper.toDomain(saved);
    }

    @Override
    public Optional<Registration> findByEmail(String email) {
        return jpaRegistrationRepository.findByEmail(email)
                .map(registrationMapper::toDomain);
    }

    @Override
    public Optional<Registration> findByReservationId(String reservationId) {
        return jpaRegistrationRepository.findByReservationId(reservationId)
                .map(registrationMapper::toDomain);
    }

    @Override
    public Optional<Registration> findById(Long id) {
        return jpaRegistrationRepository.findById(id).map(registrationMapper::toDomain);
    }

    @Override
    public List<Registration> findUnpaidReservationsExpiringBefore(LocalDateTime now, LocalDateTime expiresBefore) {
        return jpaRegistrationRepository.findUnpaidReservationsExpiringBefore(now, expiresBefore).stream()
                .map(registrationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Registration> findExpiredUnpaidRegistrations(LocalDateTime now) {
        return jpaRegistrationRepository.findExpiredUnpaidRegistrations(now).stream()
                .map(registrationMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRegistrationRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        jpaRegistrationRepository.deleteAllInBatch();
    }

    @Override
    public List<Registration> findAll() {
        return jpaRegistrationRepository.findAll().stream().map(registrationMapper::toDomain).toList();
    }
}
