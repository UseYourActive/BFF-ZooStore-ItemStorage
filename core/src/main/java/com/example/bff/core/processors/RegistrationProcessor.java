package com.example.bff.core.processors;

import com.example.bff.api.operations.RegisterRequest;
import com.example.bff.api.operations.RegisterResponse;
import com.example.bff.api.operations.RegistrationOperation;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationProcessor implements RegistrationOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse process(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

       User saved = userRepository.save(user);

        return RegisterResponse.builder()
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .phoneNumber(saved.getPhoneNumber())
                .build();
    }
}
