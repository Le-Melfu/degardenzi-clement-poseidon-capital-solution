package com.nnk.springboot.config;

import com.nnk.springboot.services.LoggerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final LoggerService logger;

    public CustomAuthenticationFailureHandler(LoggerService logger) {
        super();
        this.logger = logger;
        setDefaultFailureUrl("/app/login?error=true");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        logger.w("Authentication failed for user: {} - {}", username != null ? username : "unknown", exception.getMessage());
        super.onAuthenticationFailure(request, response, exception);
    }
}

