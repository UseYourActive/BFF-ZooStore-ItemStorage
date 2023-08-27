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

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    private final JwtFilter jwtFilter;
    private final UserRepository userRepository;
        @Bean
        public SecurityFilterChain configure(HttpSecurity http) throws Exception {
            return http
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests(c -> c
                                    .requestMatchers(HttpMethod.GET, TokenWhitelist.GET.values).permitAll()
                                    .requestMatchers(HttpMethod.POST, TokenWhitelist.POST.values).permitAll()
                                    .requestMatchers(HttpMethod.PATCH, TokenWhitelist.PATCH.values).permitAll()
                                    .requestMatchers(HttpMethod.PUT, TokenWhitelist.PUT.values).permitAll()
                                    .requestMatchers(HttpMethod.DELETE, TokenWhitelist.DELETE.values).permitAll()
                                    .requestMatchers(HttpMethod.GET, "/items", "/auth", "/cart-item","/review","/shopping-cart").authenticated()
                                    .requestMatchers(HttpMethod.PATCH, "/items", "/auth", "/cart-item","/review","/shopping-cart").authenticated()
                                    .requestMatchers("/items", "/auth", "/cart-item","/review","/shopping-cart").authenticated()
                                    .requestMatchers(HttpMethod.POST, "/items", "/auth", "/cart-item","/review","/shopping-cart").authenticated()
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
