package com.nnk.springboot.controllers;

import com.nnk.springboot.services.LoggerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for login page (under /app prefix).
 */
@Controller
@RequestMapping("app")
public class LoginController {

    private final LoggerService logger;

    public LoginController(LoggerService logger) {
        this.logger = logger;
    }

    /** Displays the login page. */
    @GetMapping("login")
    public String login() {
        logger.d("Login page accessed");
        return "login";
    }
}
