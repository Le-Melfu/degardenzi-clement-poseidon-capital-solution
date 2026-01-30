package com.nnk.springboot.security;

import com.nnk.springboot.services.LoggerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class LoginRequestLoggingFilter extends OncePerRequestFilter {

    private final LoggerService logger;

    public LoginRequestLoggingFilter(LoggerService logger) {
        this.logger = logger;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if ("/login".equals(requestURI) && "POST".equals(method)) {
            String username = request.getParameter("username");
            logger.i("Login attempt for user: {}", username != null ? username : "unknown");
        }

        filterChain.doFilter(request, response);
    }
}
