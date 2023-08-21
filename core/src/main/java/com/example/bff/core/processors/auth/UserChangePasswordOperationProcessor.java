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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserChangePasswordOperationProcessor implements UserChangePasswordOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final InvalidatedTokensRepository invalidatedTokensRepository;

    @Override
    public UserChangePasswordResponse process(UserChangePasswordRequest input) {
        User user = getAuthenticatedUser();

        if (!this.passwordEncoder.matches(input.getOldPassword(), user.getPassword())) {
            throw new CurrentPasswordInvalidException();
        }

        user.setPassword(this.passwordEncoder.encode(input.getPassword()));
        this.userRepository.save(user);

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

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}
