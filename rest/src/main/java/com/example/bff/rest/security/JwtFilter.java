package com.example.bff.rest.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.bff.core.auth.ApplicationUserDetailsService;
import com.example.bff.core.auth.JwtManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtManager jwtManager;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("JwtFilter: Request received for URI: {}", request.getRequestURI());

        Optional<String> header = Optional.ofNullable(request.getHeader("Authorization"));

        if (header.isEmpty() && !isPermittedPath(request.getRequestURI())) {
            log.warn("JwtFilter: Authorization header missing for non-permitted path: {}", request.getRequestURI());
            setInvalidTokenResponse(response);
            return;
        }

        if (header.isEmpty()) {
            log.debug("JwtFilter: Authorization header missing but path is permitted: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.get().substring(7);

        try {
            String email = jwtManager.getEmail(token);
            UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(token);

            SecurityContextHolder.getContext().setAuthentication(authToken);
            log.debug("JwtFilter: Authentication successful for user: {}", email);
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            log.warn("JwtFilter: Invalid token encountered: {}", e.getMessage());
            setInvalidTokenResponse(response);
        }
    }

    private Boolean isPermittedPath(String path) {
        return Arrays.stream(TokenWhitelist.values())
                .flatMap(e -> Arrays.stream(e.values))
                .anyMatch(e -> context.getBean(AntPathMatcher.class).match(e, path));
    }

    private void setInvalidTokenResponse(HttpServletResponse response) throws IOException {
        log.warn("JwtFilter: Setting invalid token response");
        response.setContentType("text/html");
        response.setStatus(403);
        response.setCharacterEncoding("UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.print("Invalid token.");
        printWriter.flush();
    }
}
