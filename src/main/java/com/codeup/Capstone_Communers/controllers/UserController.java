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
import com.codeup.Capstone_Communers.models.Comment;
import com.codeup.Capstone_Communers.repositories.CommentRepository;
import com.codeup.Capstone_Communers.repositories.PostRepository;
import com.codeup.Capstone_Communers.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private UserRepository userDao;
    private PostRepository postDao;
    private PasswordEncoder passwordEncoder;
    private final EntryRepository entryDao;

    private CommentRepository commentDao;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, PostRepository postDao, CommentRepository commentDao, EntryRepository entryDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.postDao = postDao;
        this.commentDao = commentDao;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> userPosts = postDao.findAllByUser(user);
        List<Comment> allPostsComments = new ArrayList<>();
        for (Post userPost : userPosts) {
            allPostsComments.addAll(commentDao.findAllByPost(userPost));
        }
        model.addAttribute("comment", new Comment());
        model.addAttribute("allComments", allPostsComments);
        model.addAttribute("user", user);
        model.addAttribute("posts", userPosts);
        return "/users/profile";
}
    @GetMapping("/journal")
    public String viewJournal(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("entry", new Entry());
        List <Entry> entries = entryDao.findAllByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("entries", entries);
        return "/users/journal";
    }

    @GetMapping("/journal/addEntry")
    public String getCreateEntry(Model model) {
        model.addAttribute("entry", new Entry());
        return "users/addEntry";
    }
    @PostMapping("/journal/addEntry")
    public String postEntry(@ModelAttribute Entry entry, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("entry", new Entry());
        Date date = new Date();
        String stringDate = date.toString();
        entry.setUser(user);
        entry.setDate(stringDate);
        System.out.println(entry);
        entryDao.save(entry);
        return "redirect:/journal";
    }
    @GetMapping("/settings")
    public String viewSettings() {
        return "/settings";
}
}