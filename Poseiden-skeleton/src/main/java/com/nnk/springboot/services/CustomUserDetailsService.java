package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoggerService logger;

    public CustomUserDetailsService(UserRepository userRepository, LoggerService logger) {
        this.userRepository = userRepository;
        this.logger = logger;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.d("Loading user: {}", username);
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            logger.e("User not found: {}", username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
        User user = userOpt.get();
        logger.i("User authenticated: {} (role: {})", user.getUsername(), user.getRole());
        return new CustomUserDetails(user);
    }
}
