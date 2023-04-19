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
import com.codeup.Capstone_Communers.models.*;
import com.codeup.Capstone_Communers.repositories.*;
import com.codeup.Capstone_Communers.models.Comment;
import com.google.gson.Gson;
import com.mysql.cj.PreparedQuery;
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
import java.util.Map;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final PostRepository postDao;
    private final PasswordEncoder passwordEncoder;
    private final EntryRepository entryDao;
    private final CommentRepository commentDao;

    private final QuestionnaireRepository questionnaireDao;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder, PostRepository postDao, CommentRepository commentDao, EntryRepository entryDao, QuestionnaireRepository questionnaireDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.entryDao = entryDao;
        this.questionnaireDao = questionnaireDao;
    }

    @GetMapping("/register")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "/users/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/questionnaire";
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
        return "/users/editEntry";
    }
    @PostMapping("/journal/{entryId}/edit")
    public String editEntry(@PathVariable long entryId, @ModelAttribute Entry entry){
        Entry ogEntry = entryDao.findById(entryId);
        entry.setId(entry.getId());
        entry.setUser(ogEntry.getUser());
        entryDao.save(entry);
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

    @GetMapping("/chat/{userId}")
    public String viewChats(Model model, @PathVariable long userId) {
        model.addAttribute("user", userDao.getReferenceById(userId));
        return "/users/chats";
    }


//    TalkJS mapping
    @GetMapping(value = "/loggedInChatUser", produces = "application/json")
    @ResponseBody
    public String loggedInChatUser() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(loggedInUser);
        Gson gson = new Gson();
        User chatUser = userDao.findById(loggedInUser.getId());
        System.out.println("chat user " + chatUser);
        return gson.toJson(loggedInUser);
    }

    @PostMapping("/follow/{postId}")
    public String followUser(@PathVariable long postId, Model model){
        User followee = postDao.findById(postId).getUser();
        List <User> followers;
        try{
            followers = followee.getFollowers();
        } catch (NullPointerException e){
            followers = new ArrayList<>();
        }
        followers.add((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        followee.setFollowers(followers);
        userDao.save(followee);
        model.addAttribute("user", new User());
        return "redirect:/forYou";
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

    @PostMapping("/updateCheckbox")
    public String updateCheckboxStatus(@RequestBody Map<String, Object> payload) {
        boolean isChecked = (boolean) payload.get("isChecked");
        Questionnaire questionnaire = userDao.getReferenceById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).getQuestionnaire();
        if(isChecked){
            questionnaire.setNotifications("Y");
        } else{
            questionnaire.setNotifications("N");
        }
        return "settings";
    }

    @GetMapping("/questionnaire")
    public String viewQuestionnaire(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User fullUser = userDao.findById(user.getId());

        //for login questionaire redirect
//                Questionnaire userQuestionnaire = questionnaireDao.findById(user.getId());
//        System.out.println(userQuestionnaire);

        if (fullUser.getQuestionnaire() == null) {
            model.addAttribute("questionnaire", new Questionnaire());
            System.out.println(fullUser);
            return "/users/questionnaire";
        } else {
            return "redirect:/discover";
        }


//        model.addAttribute("questionnaire", new Questionnaire());
//        return "/users/questionnaire";
    }
    @PostMapping("/questionnaire")
    public String postQuestionnaire(@ModelAttribute Questionnaire questionnaire) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User fullUser = userDao.findById(user.getId());
//        model.addAttribute("questionnaire", new Questionnaire());
//        questionnaire.setAnswer_1(questionnaire.getAnswer_1());
//        questionnaire.setAnswer_2(questionnaire.getAnswer_2());
//        questionnaire.setAnswer_3(questionnaire.getAnswer_3());

        questionnaire.setUser(fullUser);
        questionnaireDao.save(questionnaire);
        fullUser.setQuestionnaire(questionnaire);
        userDao.save(fullUser);
        return "redirect:/discover";
    }
}