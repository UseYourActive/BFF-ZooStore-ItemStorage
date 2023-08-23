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

import static com.example.bff.core.config.UserLoggerMessages.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserRegisterOperationProcessor implements UserRegisterOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRegisterResponse process(UserRegisterRequest input) {
        log.info(STARTING_USER_REGISTRATION_PROCESS_FOR_EMAIL, input.getEmail());

        User user = User.builder()
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNumber(input.getPhoneNumber())
                .build();

        log.info(CREATING_USER_OBJECT_FOR_EMAIL, input.getEmail());

        User persisted = this.userRepository.save(user);
        log.info(USER_REGISTERED_SUCCESSFULLY_WITH_EMAIL, input.getEmail());

        UserRegisterResponse build = UserRegisterResponse.builder()
                .id(persisted.getId())
                .email(persisted.getEmail())
                .firstName(persisted.getFirstName())
                .lastName(persisted.getLastName())
                .phoneNumber(persisted.getPhoneNumber())
                .build();

        log.info(USER_REGISTRATION_PROCESS_COMPLETED_FOR_EMAIL, input.getEmail());

        return build;
    }
}
