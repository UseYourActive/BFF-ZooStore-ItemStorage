package com.example.bff.core.processors.auth;

import com.example.bff.api.operations.auth.login.UserLoginOperation;
import com.example.bff.api.operations.auth.login.UserLoginRequest;
import com.example.bff.api.operations.auth.login.UserLoginResponse;
import com.example.bff.core.auth.JwtManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.bff.core.config.UserLoggerMessages.JWT_GENERATED_SUCCESSFULLY_FOR_USER_WITH_EMAIL;
import static com.example.bff.core.config.UserLoggerMessages.PROCESSING_USER_LOGIN_REQUEST;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserLoginOperationProcessor implements UserLoginOperation {
    private final JwtManager jwtManager;

    @Override
    public UserLoginResponse process(UserLoginRequest input) {
        log.info(PROCESSING_USER_LOGIN_REQUEST);

        String jwtToken = this.jwtManager.generateJwt(input);
        log.info(JWT_GENERATED_SUCCESSFULLY_FOR_USER_WITH_EMAIL, input.getEmail());

        return UserLoginResponse.builder()
                .jwt(jwtToken)
                .build();
    }
}
