package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the home page.
 */
@Controller
public class HomeController {
    /** Displays the home page. */
    @RequestMapping("/")
    public String home() {
        return "home";
    }
}
