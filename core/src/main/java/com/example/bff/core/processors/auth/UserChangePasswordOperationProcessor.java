package com.example.bff.core.processors.auth;

import com.example.bff.api.operations.auth.changepassword.UserChangePasswordOperation;
import com.example.bff.api.operations.auth.changepassword.UserChangePasswordRequest;
import com.example.bff.api.operations.auth.changepassword.UserChangePasswordResponse;
import com.example.bff.core.exceptions.CurrentPasswordInvalidException;
import com.example.bff.persistence.entities.Token;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.InvalidatedTokensRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserChangePasswordOperationProcessor implements UserChangePasswordOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InvalidatedTokensRepository invalidatedTokensRepository;

    @Override
    public UserChangePasswordResponse process(UserChangePasswordRequest input) {
        User user = getAuthenticatedUser();
        log.info("User found in database with id = {}", user.getId());

        if (!this.passwordEncoder.matches(input.getOldPassword(), user.getPassword())) {
            log.error("Password does not match!");
            throw new CurrentPasswordInvalidException();
        }

        user.setPassword(this.passwordEncoder.encode(input.getPassword()));
        log.info("Successfully changed user password with id = {}", user.getId());

        this.userRepository.save(user);
        log.info("Successfully saved user in database with id = {}", user.getId());

        this.invalidatedTokensRepository.save(
                Token.builder()
                        .token((String) SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getDetails())
                        .build()
        );

        return new UserChangePasswordResponse();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        log.info("Authenticated user with email = {}", email);

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email '{}' not found", email);
                    return new UsernameNotFoundException("Email does not exist!");
                });
    }
}
