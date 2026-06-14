package com.planetaluzu.auth.web;

import com.planetaluzu.auth.web.dto.AdminLoginRequest;
import com.planetaluzu.auth.web.dto.AdminSessionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/admin")
@RequiredArgsConstructor
public class AdminAuthController {
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AdminSessionResponse login(
            @RequestBody AdminLoginRequest request,
            HttpServletRequest httpRequest
    ) {
        if (request.email() == null || request.password() == null) {
            throw new BadCredentialsException("Invalid admin credentials");
        }

        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        request.email().trim().toLowerCase(),
                        request.password()
                )
        );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        httpRequest.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                securityContext
        );

        return toSessionResponse(authentication);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public AdminSessionResponse me(Authentication authentication) {
        return toSessionResponse(authentication);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        if (request.getSession(false) != null) {
            request.getSession(false).invalidate();
        }
    }

    private AdminSessionResponse toSessionResponse(Authentication authentication) {
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("ROLE_ADMIN");

        return new AdminSessionResponse(authentication.getName(), role);
    }
}
