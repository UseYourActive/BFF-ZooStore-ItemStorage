package com.example.bff.core.processors.auth;

import com.example.bff.api.operations.auth.login.LoginUserOperation;
import com.example.bff.api.operations.auth.login.LoginUserRequest;
import com.example.bff.api.operations.auth.login.LoginUserResponse;
import com.example.bff.core.auth.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginUserOperationProcessor implements LoginUserOperation {
    private final JwtManager jwtManager;

    @Override
    public LoginUserResponse process(LoginUserRequest request) {
        return LoginUserResponse.builder()
                .jwt(this.jwtManager.generateJwt(request))
                .build();
    }
}
