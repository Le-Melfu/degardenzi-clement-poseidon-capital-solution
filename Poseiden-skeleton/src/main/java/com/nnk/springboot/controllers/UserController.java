package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserCreateDTO;
import com.nnk.springboot.dto.user.UserUpdateDTO;
import com.nnk.springboot.services.interfaces.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
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

/**
 * Controller for User CRUD operations (admin only).
 */
@Controller
public class UserController {
    private final UserService userService;
    private final PasswordValidator passwordValidator;

    public UserController(UserService userService,
            PasswordValidator passwordValidator) {
        this.userService = userService;
        this.passwordValidator = passwordValidator;
    }

    /** Displays the list of all users (admin only). */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAll(PageRequest.of(0, 1000)).getContent());
        return "user/list";
    }

    /** Displays the form to add a new user (admin only). */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    /** Validates and saves a new user (admin only). */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            result.rejectValue("password", "error.password", "Password is mandatory");
        } else {
            if (!passwordValidator.isValid(user.getPassword(), null)) {
                result.rejectValue("password", "error.password",
                        "Password must be at least 8 characters, contain at least one uppercase letter, one digit, and one symbol");
            }
        }
        if (!result.hasErrors()) {
            UserCreateDTO dto = UserCreateDTO.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .fullName(user.getFullName())
                    .role(user.getRole() != null ? user.getRole() : Role.USER)
                    .build();
            userService.create(dto);
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /** Displays the form to update an existing user (admin only). */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") @NonNull Long id, Model model) {
        var userResponse = userService.findById(id);
        User user = User.builder()
                .id(userResponse.getId())
                .username(userResponse.getUsername())
                .fullName(userResponse.getFullName())
                .role(userResponse.getRole())
                .password("")
                .build();
        model.addAttribute("user", user);
        return "user/update";
    }

    /** Updates an existing user (admin only). */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") @NonNull Long id, @Valid User user,
            BindingResult result, Model model) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            if (!passwordValidator.isValid(user.getPassword(), null)) {
                result.rejectValue("password", "error.password",
                        "Password must be at least 8 characters, contain at least one uppercase letter, one digit, and one symbol");
            }
        }
        if (result.hasErrors()) {
            return "user/update";
        }

        UserUpdateDTO dto = UserUpdateDTO.builder()
                .fullName(user.getFullName())
                .role(user.getRole())
                .password(user.getPassword() != null && !user.getPassword().isEmpty() ? user.getPassword() : null)
                .build();
        userService.update(id, dto);
        return "redirect:/user/list";
    }

    /** Deletes a user by its ID (admin only). */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") @NonNull Long id) {
        userService.delete(id);
        return "redirect:/user/list";
    }
}
