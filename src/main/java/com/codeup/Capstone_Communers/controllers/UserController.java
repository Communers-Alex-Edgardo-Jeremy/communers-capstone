package com.codeup.Capstone_Communers.controllers;

//import com.codeup.Capstone_Communers.repositories.UserRepository;
//import org.apache.catalina.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;

import com.codeup.Capstone_Communers.models.Comment;
import com.codeup.Capstone_Communers.models.Post;
import com.codeup.Capstone_Communers.models.User;
import com.codeup.Capstone_Communers.repositories.CommentRepository;
import com.codeup.Capstone_Communers.repositories.PostRepository;
import com.codeup.Capstone_Communers.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private UserRepository userDao;

    private PostRepository postDao;
    private PasswordEncoder passwordEncoder;

    private CommentRepository commentDao;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, PostRepository postDao, CommentRepository commentDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.postDao = postDao;
        this.commentDao = commentDao;
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
    public String viewJournal() {
        return "/users/journal";
    }
    @GetMapping("/settings")
    public String viewSettings(Model model) {
        model.addAttribute("user", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "/settings";
    }
    @PostMapping("/user/edit")
    public String editUser(Model model, @ModelAttribute User user) {
        User oldUserDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User wholeUser = userDao.findById(oldUserDetails.getId());
        try{
            wholeUser.setFirst_name(user.getFirst_name());
            wholeUser.setLast_name(user.getLast_name());
            wholeUser.setEmail(user.getEmail());
            wholeUser.setUsername(user.getUsername());
            wholeUser.setPassword(passwordEncoder.encode(user.getPassword()));
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        userDao.save(wholeUser);
        model.addAttribute("user", wholeUser);
        return "/settings";
    }

}