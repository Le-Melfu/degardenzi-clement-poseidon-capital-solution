package com.nnk.springboot.config;

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

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        
        // Log all requests to /login
        if ("/login".equals(requestURI) && "POST".equals(method)) {
            System.out.println("[DebugClem] ========== LOGIN POST REQUEST ==========");
            System.out.println("[DebugClem] - Request URI: " + requestURI);
            System.out.println("[DebugClem] - Request method: " + method);
            System.out.println("[DebugClem] - Request URL: " + request.getRequestURL());
            System.out.println("[DebugClem] - Username parameter: " + request.getParameter("username"));
            System.out.println("[DebugClem] - Password parameter present: " + (request.getParameter("password") != null));
            
            // Log CSRF token
            String csrfToken = request.getParameter("_csrf");
            if (csrfToken == null) {
                csrfToken = request.getHeader("X-XSRF-TOKEN");
            }
            System.out.println("[DebugClem] - CSRF token present: " + (csrfToken != null));
            
            // Log session
            System.out.println("[DebugClem] - Session ID: " + (request.getSession(false) != null ? request.getSession().getId() : "No session"));
            System.out.println("[DebugClem] ========================================");
        }
        
        filterChain.doFilter(request, response);
    }
}

