package com.planetaluzu.auth.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUserJpaEntity, Long> {
    Optional<AdminUserJpaEntity> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
