package com.codeup.Capstone_Communers.controllers;

//import com.codeup.Capstone_Communers.repositories.UserRepository;
//import org.apache.catalina.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;

import com.codeup.Capstone_Communers.models.User;
import com.codeup.Capstone_Communers.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private UserRepository userDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showSignupForm(Model model){

        model.addAttribute("user", new com.codeup.Capstone_Communers.models.User());
        return "users/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
//        User user = userDao.getReferenceById(id);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        return "/users/profile";

}
    @GetMapping("/settings")
    public String viewSettings() {
        return "/settings";
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