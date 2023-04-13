package com.codeup.Capstone_Communers.controllers;


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

}
