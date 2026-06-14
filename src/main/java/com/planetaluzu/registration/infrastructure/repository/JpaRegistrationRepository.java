package com.planetaluzu.registration.infrastructure.repository;

import com.planetaluzu.registration.infrastructure.entity.RegistrationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaRegistrationRepository extends JpaRepository<RegistrationJpaEntity, Long> {
    Optional<RegistrationJpaEntity> findByEmail(String email);

    Optional<RegistrationJpaEntity> findByReservationId(String reservationId);

    @Query("""
                SELECT r FROM RegistrationJpaEntity r
                WHERE r.isPaid = false
                AND r.expiresAt > :now
                AND r.expiresAt <= :expiresBefore
                AND COALESCE(r.expirationReminderSent, false) = false
            """)
    List<RegistrationJpaEntity> findUnpaidReservationsExpiringBefore(LocalDateTime now, LocalDateTime expiresBefore);

    @Query("""
                SELECT r FROM RegistrationJpaEntity r
                WHERE r.isPaid = false
                AND r.expiresAt < :now
            """)
    List<RegistrationJpaEntity> findExpiredUnpaidRegistrations(LocalDateTime now);
}
