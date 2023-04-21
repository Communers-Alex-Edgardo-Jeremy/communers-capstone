package com.codeup.Capstone_Communers.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
class HomeController {
    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/landing";
    }

//    @GetMapping("/verify")
//    public String verify() {
//        return "/users/process_register";
//    }

    @GetMapping("/landing")
    public String landingPage(){
        return "/users/landing";
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
