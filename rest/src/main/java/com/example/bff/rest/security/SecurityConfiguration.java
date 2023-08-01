package com.example.bff.rest.security;

import com.example.bff.core.auth.ApplicationUserDetailsService;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {
    private final UserRepository userRepository;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c -> c
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items","/auth").authenticated()
                        .requestMatchers(HttpMethod.GET,"/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html").permitAll()
                )
                .cors(c -> c.disable())
                .csrf(c -> c.disable())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public UserDetailsService getApplicationUserDetailsService() {
        return new ApplicationUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}