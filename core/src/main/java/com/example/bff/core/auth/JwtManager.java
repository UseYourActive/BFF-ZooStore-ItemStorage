package com.example.bff.core.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bff.api.operations.auth.login.UserLoginRequest;
import com.example.bff.core.exceptions.InvalidCredentialsException;
import com.example.bff.core.exceptions.JWTVerificationException;
import com.example.bff.persistence.repositories.InvalidatedTokensRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtManager {
    private final Duration TOKEN_VALIDITY = Duration.of(30, ChronoUnit.DAYS);

    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final InvalidatedTokensRepository invalidatedTokensRepository;
    private final ApplicationContext context;

    @Value("${jwt-secret}")
    private String jwtSecret;

    public String generateJwt(UserLoginRequest input) {
        log.debug("JwtManager: Generating JWT for user: {}", input.getEmail());

        UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(input.getEmail());

        if (!context.getBean(PasswordEncoder.class).matches(input.getPassword(), userDetails.getPassword())) {
            log.warn("JwtManager: Invalid credentials provided for user: {}", input.getEmail());
            throw new InvalidCredentialsException();
        }

        return JWT.create()
                .withClaim("email", userDetails.getUsername())
                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(TOKEN_VALIDITY))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String getEmail(String jwt) {
        log.debug("JwtManager: Attempting to verify JWT and extract email");

        if (this.invalidatedTokensRepository.existsByToken(jwt)) {
            log.warn("JwtManager: Attempted to verify blacklisted token: {}", jwt);
            throw new JWTVerificationException("Token blacklisted.");
        }

        DecodedJWT decoded = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withClaimPresence("email")
                .build()
                .verify(jwt);

        String email = decoded.getClaim("email").asString();
        log.debug("JwtManager: JWT verified successfully for user: {}", email);

        return email;
    }
}