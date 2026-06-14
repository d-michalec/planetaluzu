package com.planetaluzu.auth.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserBootstrap implements CommandLineRunner {
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_EMAIL:}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD:}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (adminEmail == null || adminEmail.isBlank() || adminPassword == null || adminPassword.isBlank()) {
            return;
        }

        String normalizedEmail = adminEmail.trim().toLowerCase();
        if (adminUserRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            return;
        }

        adminUserRepository.save(new AdminUserJpaEntity(
                normalizedEmail,
                passwordEncoder.encode(adminPassword),
                ADMIN_ROLE
        ));
    }
}
