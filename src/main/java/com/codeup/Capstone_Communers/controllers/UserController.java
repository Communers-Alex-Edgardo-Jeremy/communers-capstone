package com.codeup.Capstone_Communers.controllers;

//import com.codeup.Capstone_Communers.repositories.UserRepository;
//import org.apache.catalina.User;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;

import com.codeup.Capstone_Communers.SecurityConfiguration;
import com.codeup.Capstone_Communers.models.Comment;
import com.codeup.Capstone_Communers.models.Entry;
import com.codeup.Capstone_Communers.models.Post;
import com.codeup.Capstone_Communers.models.User;
import com.codeup.Capstone_Communers.repositories.EntryRepository;
import com.codeup.Capstone_Communers.models.Comment;
import com.codeup.Capstone_Communers.repositories.CommentRepository;
import com.codeup.Capstone_Communers.repositories.PostRepository;
import com.codeup.Capstone_Communers.repositories.UserRepository;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContext;
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
    @GetMapping("/journal/{entryId}/edit")
    public String editEntry(Model model, @PathVariable long entryId){
        Entry entry = entryDao.findById(entryId);
        model.addAttribute("entry", entry);

//        model.addAttribute("entry", entryDao.getReferenceById(entryId));
        return "/users/editEntry";
    }
    @PostMapping("/journal/{entryId}/edit")
    public String editEntry(@PathVariable long entryId, @ModelAttribute Entry entry){
        Entry ogEntry = entryDao.findById(entryId);
        entry.setId(entry.getId());
        entry.setUser(ogEntry.getUser());
        entryDao.save(entry);
//
//        User user = userDao.findById(entryId);
//        editedEntry.setUser(user);
//        entryDao.save(editedEntry);
        return "redirect:/journal";
    }
    @GetMapping("/journal/{entryId}/delete")
    public String deleteComment(@PathVariable long entryId){
        entryDao.delete(entryDao.getReferenceById(entryId));
        return "redirect:/journal";
    }
    @GetMapping("/settings")
    public String viewSettings(Model model) {
        model.addAttribute("user", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "/settings";
    }


    @GetMapping("/about")
    public String viewAboutUs(Model model) {
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        return "/about";
    }
    
    @GetMapping("/resources")
    public String viewResources() {
        return "/resources";
    }
    
    @GetMapping("/chats")
    public String viewChats() {
        return "/users/chats";
    }


//    TalkJS mapping
    @GetMapping(value = "/loggedInChatUser", produces = "application/json")
    @ResponseBody
    public String loggedInChatUser() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gson gson = new Gson();
        User chatUser = userDao.findById(loggedInUser.getId());
        System.out.println(gson.toJson(chatUser));
        return gson.toJson(chatUser);
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
    
    @PostMapping("/user/delete")
    public String deleteUser(HttpServletRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userDao.delete(userDao.getReferenceById(user.getId()));
        try {
            request.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/login";
    }
}