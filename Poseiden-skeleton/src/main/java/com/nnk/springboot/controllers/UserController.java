package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.validation.PasswordValidator;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordValidator passwordValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidator = passwordValidator;
    }

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            result.rejectValue("password", "error.password", "Password is mandatory");
        } else {
            if (!passwordValidator.isValid(user.getPassword(), null)) {
                result.rejectValue("password", "error.password", "Password must be at least 8 characters, contain at least one uppercase letter, one digit, and one symbol");
            }
        }
        if (user.getUsername() != null && userRepository.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.username", "Username already exists");
        }
        if (!result.hasErrors()) {
            user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
            user.setPassword(null);
            if (user.getRole() == null) {
                user.setRole(Role.USER);
            }
            userRepository.save(user);
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") @NonNull Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") @NonNull Long id, @Valid User user,
            BindingResult result, Model model) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            if (!passwordValidator.isValid(user.getPassword(), null)) {
                result.rejectValue("password", "error.password", "Password must be at least 8 characters, contain at least one uppercase letter, one digit, and one symbol");
            }
        }
        if (result.hasErrors()) {
            return "user/update";
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getFullName() != null) {
            existingUser.setFullName(user.getFullName());
        }
        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        userRepository.save(existingUser);
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") @NonNull Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/user/list";
    }
}
