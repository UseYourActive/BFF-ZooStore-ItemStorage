package com.example.bff.rest.auth;

import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.bff.persistence.entities.User userFoundInRepo = userRepository.findUserByEmail(email)
                .orElseThrow(); // add exception

        return new User(userFoundInRepo.getEmail(),
                userFoundInRepo.getPassword(),
                Set.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
