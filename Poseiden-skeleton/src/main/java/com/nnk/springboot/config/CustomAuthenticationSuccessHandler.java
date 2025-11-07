package com.nnk.springboot.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public CustomAuthenticationSuccessHandler() {
        super();
        setDefaultTargetUrl("/bidList/list");
        setAlwaysUseDefaultTargetUrl(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        System.out.println("[DebugClem] ========== AUTHENTICATION SUCCESS ==========");
        System.out.println("[DebugClem] - User: " + authentication.getName());
        System.out.println("[DebugClem] - Authorities: " + authentication.getAuthorities());
        System.out.println("[DebugClem] - Request method: " + request.getMethod());
        System.out.println("[DebugClem] - Request URI: " + request.getRequestURI());
        System.out.println("[DebugClem] - Request URL: " + request.getRequestURL());
        System.out.println("[DebugClem] - Session ID: " + (request.getSession(false) != null ? request.getSession().getId() : "No session"));
        System.out.println("[DebugClem] - Default target URL: " + getDefaultTargetUrl());
        System.out.println("[DebugClem] - Redirecting to: /bidList/list");
        System.out.println("[DebugClem] - Response status before redirect: " + response.getStatus());
        super.onAuthenticationSuccess(request, response, authentication);
        System.out.println("[DebugClem] - Response status after redirect: " + response.getStatus());
        System.out.println("[DebugClem] - Response committed: " + response.isCommitted());
        System.out.println("[DebugClem] ============================================");
    }
}

