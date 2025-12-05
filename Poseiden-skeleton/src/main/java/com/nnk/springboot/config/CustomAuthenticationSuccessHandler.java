package com.nnk.springboot.config;

import com.nnk.springboot.services.LoggerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final LoggerService logger;

    public CustomAuthenticationSuccessHandler(LoggerService logger) {
        super();
        this.logger = logger;
        setDefaultTargetUrl("/bidList/list");
        setAlwaysUseDefaultTargetUrl(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        logger.i("Authentication successful for user: {}", authentication.getName());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

