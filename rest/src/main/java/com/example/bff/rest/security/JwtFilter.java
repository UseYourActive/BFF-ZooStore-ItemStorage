package com.example.bff.rest.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.bff.core.auth.ApplicationUserDetailsService;
import com.example.bff.core.auth.JwtManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtManager jwtManager;
    private final ApplicationUserDetailsService applicationUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> header = Optional.ofNullable(request.getHeader("Authorization"));
        if (header.isEmpty()) {
            filterChain.doFilter(request, response);
        }

        if (header.isPresent()) {
            String token = header.get().substring(7);

            try {
                String email = jwtManager.getEmail(token);
                UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(email);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
                filterChain.doFilter(request, response);
            } catch (JWTVerificationException e) {
                response.setContentType("application/json");
                response.setStatus(403);
                response.setCharacterEncoding("UTF-8");

                PrintWriter printWriter = response.getWriter();
                printWriter.print("Invalid token.");
                printWriter.flush();
            }
        }
    }
}
