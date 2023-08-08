package com.example.bff.rest.security;

import com.example.bff.api.operations.auth.login.LoginUserRequest;
import com.example.bff.api.operations.auth.login.LoginUserResponse;
import com.example.bff.api.operations.auth.register.RegisterUserRequest;
import com.example.bff.api.operations.auth.register.RegisterUserResponse;
import com.example.bff.persistence.entities.Role;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterUserResponse register(RegisterUserRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User save = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return RegisterUserResponse.builder()
                .firstName(save.getFirstName())
                .lastName(save.getLastName())
                .phoneNumber(save.getPhoneNumber())
                .email(save.getEmail())
                .jwt(token)
                .build();
    }

    public LoginUserResponse login(LoginUserRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("Username not found!"));

        String token = jwtService.generateToken(user);

        return LoginUserResponse.builder()
                .jwt(token)
                .build();
    }
}
