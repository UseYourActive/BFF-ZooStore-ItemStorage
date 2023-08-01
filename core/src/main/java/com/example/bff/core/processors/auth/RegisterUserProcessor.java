package com.example.bff.core.processors.auth;

import com.example.bff.api.operations.auth.register.RegisterUserRequest;
import com.example.bff.api.operations.auth.register.RegisterUserResponse;
import com.example.bff.api.operations.auth.register.RegisterUserOperation;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterUserProcessor implements RegisterUserOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResponse process(RegisterUserRequest registerRequest) {
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

       User saved = userRepository.save(user);

        return RegisterUserResponse.builder()
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .phoneNumber(saved.getPhoneNumber())
                .build();
    }
}
