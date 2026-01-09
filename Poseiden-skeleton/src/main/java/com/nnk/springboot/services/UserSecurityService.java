package com.nnk.springboot.services;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("userSecurityService")
public class UserSecurityService {

    public boolean isOwner(Authentication authentication, Long userId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            Long currentUserId = ((CustomUserDetails) principal).getUserId();
            return currentUserId.equals(userId);
        }
        return false;
    }
}

