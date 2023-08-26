package com.example.bff.core.auth;

import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.debug("ApplicationUserDetailsService: Loading user by username: {}", email);

        com.example.bff.persistence.entities.User persistedUser = this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("ApplicationUserDetailsService: User with email {} not found", email);
                    return new UsernameNotFoundException("Email not found");
                });
        log.debug("ApplicationUserDetailsService: User loaded successfully: {}", email);

        return new User(
                persistedUser.getEmail(),
                persistedUser.getPassword(),
                Set.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
