package com.codeup.Capstone_Communers.controllers;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
class HomeController {
    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/login";
    }

    @GetMapping("/post")
    public String loginSuccess() {
        return "/posts/discover";
    }

    @GetMapping("/about")
    public String viewAbout() {
        return "/about";
    }
    @GetMapping("/resources")
    public String viewResources() {
        return "/resources";
    }
}
