package com.example.bff.core.processors.auth;

import com.example.bff.api.operations.auth.register.UserRegisterOperation;
import com.example.bff.api.operations.auth.register.UserRegisterRequest;
import com.example.bff.api.operations.auth.register.UserRegisterResponse;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserRegisterOperationProcessor implements UserRegisterOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRegisterResponse process(final UserRegisterRequest input) {
        log.info("Starting user registration process for email = {}", input.getEmail());

        User user = User.builder()
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNumber(input.getPhoneNumber())
                .build();
        log.info("Creating user object for email = {}", input.getEmail());

        User persisted = this.userRepository.save(user);
        log.info("User registered successfully with email: {}", input.getEmail());

        UserRegisterResponse build = UserRegisterResponse.builder()
                .id(String.valueOf(persisted.getId()))
                .email(persisted.getEmail())
                .firstName(persisted.getFirstName())
                .lastName(persisted.getLastName())
                .phoneNumber(persisted.getPhoneNumber())
                .build();
        log.info("User registration process completed for email = {}", input.getEmail());

        return build;
    }
}
