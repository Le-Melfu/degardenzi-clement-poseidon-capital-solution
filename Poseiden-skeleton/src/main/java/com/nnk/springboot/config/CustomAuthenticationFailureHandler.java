package com.nnk.springboot.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public CustomAuthenticationFailureHandler() {
        super();
        setDefaultFailureUrl("/app/login?error=true");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        System.out.println("[DebugClem] ========== AUTHENTICATION FAILURE ==========");
        System.out.println("[DebugClem] - Exception type: " + exception.getClass().getName());
        System.out.println("[DebugClem] - Exception message: " + exception.getMessage());
        System.out.println("[DebugClem] - Request method: " + request.getMethod());
        System.out.println("[DebugClem] - Request URI: " + request.getRequestURI());
        System.out.println("[DebugClem] - Username parameter: " + request.getParameter("username"));
        System.out.println("[DebugClem] - Password parameter present: " + (request.getParameter("password") != null));
        System.out.println("[DebugClem] - Redirecting to: /app/login?error=true");
        System.out.println("[DebugClem] ============================================");
        super.onAuthenticationFailure(request, response, exception);
    }
}

