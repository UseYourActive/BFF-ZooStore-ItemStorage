package com.example.bff.core.processors.auth;

import com.example.bff.api.operations.auth.login.UserLoginOperation;
import com.example.bff.api.operations.auth.login.UserLoginRequest;
import com.example.bff.api.operations.auth.login.UserLoginResponse;
import com.example.bff.core.auth.JwtManager;
import com.example.bff.core.processors.jwt.JwtService;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserLoginOperationProcessor implements UserLoginOperation {
    private final JwtManager jwtManager;

    @Override
    public UserLoginResponse process(UserLoginRequest input) {
        return UserLoginResponse.builder()
                .jwt(this.jwtManager.generateJwt(input))
                .build();
    }
}
