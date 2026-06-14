package com.planetaluzu.auth.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {
    private final AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminUserJpaEntity admin = adminUserRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        return User.withUsername(admin.getEmail())
                .password(admin.getPasswordHash())
                .authorities(admin.getRole())
                .disabled(!admin.isEnabled())
                .build();
    }
}
