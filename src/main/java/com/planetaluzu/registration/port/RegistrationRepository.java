package com.planetaluzu.registration.port;

import com.planetaluzu.registration.domain.Registration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository {

    Registration save(Registration registration);

    Optional<Registration> findByEmail(String email);

    Optional<Registration> findByReservationId(String reservationId);

    Optional<Registration> findById(Long id);

    List<Registration> findUnpaidReservationsExpiringBefore(LocalDateTime now, LocalDateTime expiresBefore);

    List<Registration> findExpiredUnpaidRegistrations(LocalDateTime now);

    void deleteById(Long id);

    void deleteAll();

    List<Registration> findAll();
}
