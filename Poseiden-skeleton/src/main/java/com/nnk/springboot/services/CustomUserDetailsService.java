package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("[DebugClem] - Searching for user with username: '" + username + "'");
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            System.out.println("[DebugClem] - User not found in database for username: '" + username + "'");
            throw new UsernameNotFoundException("User not found: " + username);
        }
        User user = userOpt.get();
        System.out.println("[DebugClem] - User found: " + user.getUsername() + " (role: " + user.getRole() + ")");
        return new CustomUserDetails(user);
    }
}
