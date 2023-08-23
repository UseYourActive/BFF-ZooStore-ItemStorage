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

import static com.example.bff.core.config.UserLoggerMessages.*;

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
        log.info(USER_FOUND_IN_DATABASE_WITH_ID, user.getId());

        if (!this.passwordEncoder.matches(input.getOldPassword(), user.getPassword())) {
            log.error(PASSWORD_DOES_NOT_MATCH);
            throw new CurrentPasswordInvalidException();
        }

        user.setPassword(this.passwordEncoder.encode(input.getPassword()));
        log.info(SUCCESSFULLY_CHANGED_USER_PASSWORD_WITH_ID, user.getId());

        this.userRepository.save(user);
        log.info(SUCCESSFULLY_SAVED_USER_IN_DATABASE_WITH_ID, user.getId());

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

        log.info(AUTHENTICATED_USER_WITH_EMAIL, email);

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error(USER_WITH_EMAIL_NOT_FOUND, email);
                    return new UsernameNotFoundException(EMAIL_DOES_NOT_EXIST);
                });
    }
}
