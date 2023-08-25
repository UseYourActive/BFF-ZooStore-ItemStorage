package com.example.bff.core.processors.auth;

import com.example.bff.api.operations.auth.login.UserLoginOperation;
import com.example.bff.api.operations.auth.login.UserLoginRequest;
import com.example.bff.api.operations.auth.login.UserLoginResponse;
import com.example.bff.core.auth.JwtManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserLoginOperationProcessor implements UserLoginOperation {
    private final JwtManager jwtManager;

    @Override
    public UserLoginResponse process(UserLoginRequest input) {
        log.info("Processing user login request");

        String jwtToken = this.jwtManager.generateJwt(input);
        log.info("JWT generated successfully for user with email: {}", input.getEmail());

        return UserLoginResponse.builder()
                .jwt(jwtToken)
                .build();
    }
}
