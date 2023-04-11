package com.codeup.Capstone_Communers.controllers;

import com.codeup.Capstone_Communers.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String showLoginForm() {

        return "users/login";

    }

}
