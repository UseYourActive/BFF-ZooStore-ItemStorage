package com.example.bff.rest.security.auth;

import com.example.bff.api.operations.auth.changepassword.ChangeUserPasswordOperation;
import com.example.bff.api.operations.auth.changepassword.ChangeUserPasswordRequest;
import com.example.bff.api.operations.auth.changepassword.ChangeUserPasswordResponse;
import com.example.bff.core.exceptions.CurrentPasswordInvalidException;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChangeUserPasswordOperationProcessor implements ChangeUserPasswordOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ChangeUserPasswordResponse process(ChangeUserPasswordRequest changeUserPasswordRequest) {
        User user = getAuthenticatedUser();

        if (!this.passwordEncoder.matches(changeUserPasswordRequest.getOldPassword(), user.getPassword())) {
            throw new CurrentPasswordInvalidException();
        }

        user.setPassword(this.passwordEncoder.encode(changeUserPasswordRequest.getNewPassword()));
        this.userRepository.save(user);

        return new ChangeUserPasswordResponse();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}
