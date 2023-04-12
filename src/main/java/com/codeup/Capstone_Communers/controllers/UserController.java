package com.codeup.Capstone_Communers.controllers;

//import com.codeup.Capstone_Communers.repositories.UserRepository;
//import org.apache.catalina.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;

import com.codeup.Capstone_Communers.models.Entry;
import com.codeup.Capstone_Communers.models.Post;
import com.codeup.Capstone_Communers.models.User;
import com.codeup.Capstone_Communers.repositories.EntryRepository;
import com.codeup.Capstone_Communers.repositories.PostRepository;
import com.codeup.Capstone_Communers.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    private UserRepository userDao;
    private PostRepository postDao;
    private PasswordEncoder passwordEncoder;
    private final EntryRepository entryDao;


    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, PostRepository postDao, EntryRepository entryDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.postDao = postDao;
        this.entryDao = entryDao;
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
        List<Post> userPosts = postDao.findAllByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("posts", userPosts);

        return "/users/profile";

}
    @GetMapping("/journal")
    public String viewJournal(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("entry", new Entry());
        List <Entry> entries = entryDao.findAllByUser(user);
        model.addAttribute("entries", entries);
        return "/users/journal";
    }

    @PostMapping("/journal")
    public String postJournal(@ModelAttribute Entry entry, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId());
        model.addAttribute("journal", new Entry());
        entry.setUser(user);
        Date date = new Date();
        String stringDate = date.toString();
        entry.setDate(stringDate);
        entryDao.save(entry);
        return "/users/journal";
    }
    @GetMapping("/settings")
    public String viewSettings() {
        return "/settings";
}
}