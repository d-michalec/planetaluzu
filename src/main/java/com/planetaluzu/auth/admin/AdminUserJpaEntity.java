package com.planetaluzu.auth.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_users")
public class AdminUserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected AdminUserJpaEntity() {
    }

    public AdminUserJpaEntity(String email, String passwordHash, String role) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.enabled = true;
        this.createdAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
