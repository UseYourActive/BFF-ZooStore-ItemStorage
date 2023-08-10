package com.example.bff.core.processors.user;

import com.example.bff.api.operations.auth.changepassword.UserChangePasswordOperation;
import com.example.bff.api.operations.auth.changepassword.UserChangePasswordRequest;
import com.example.bff.api.operations.auth.changepassword.UserChangePasswordResponse;
import com.example.bff.core.exceptions.NoSuchUserException;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserChangePasswordOperationProcessor implements UserChangePasswordOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserChangePasswordResponse process(UserChangePasswordRequest input) {
        User user = this.userRepository.findByEmail(input.getEmail())
                .orElseThrow(NoSuchUserException::new);

        String hashedPassword = this.passwordEncoder.encode(input.getPassword());

        User userWithChangedPassword = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(hashedPassword)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();

        this.userRepository.save(userWithChangedPassword);

        return UserChangePasswordResponse
                .builder()
                .email(userWithChangedPassword.getEmail())
                .firstName(userWithChangedPassword.getFirstName())
                .lastName(userWithChangedPassword.getLastName())
                .phoneNumber(userWithChangedPassword.getPhoneNumber())
                .build();
    }
}
