package com.example.bff.core.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bff.api.operations.auth.login.LoginUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Component
public class JwtManager {
    private final Duration TOKEN_VALIDITY = Duration.of(30, ChronoUnit.DAYS);
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @Value("${jwt-secret}")
    private String jwtSecret;

    public String generateJwt(LoginUserRequest request) {
        UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(request.getEmail());

        return JWT.create()
                .withClaim("email", userDetails.getUsername())
                .withClaim("roles", userDetails.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .toList())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(TOKEN_VALIDITY))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String getEmail(String jwt) {
        DecodedJWT decoded = JWT.require(Algorithm.HMAC256(jwtSecret))
                .withClaimPresence("email")
                .build()
                .verify(jwt);

        return decoded.getClaim("email").asString();
    }
}
