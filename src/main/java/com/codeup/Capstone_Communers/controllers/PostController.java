package com.codeup.Capstone_Communers.controllers;

import com.codeup.Capstone_Communers.models.*;
import com.codeup.Capstone_Communers.Services.Utility;
import com.codeup.Capstone_Communers.repositories.CommentRepository;
import com.codeup.Capstone_Communers.repositories.CommunityRepository;
import com.codeup.Capstone_Communers.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.Capstone_Communers.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {


    private final CommunityRepository communityDao;
    private final PostRepository postDao;
    private final CommentRepository commentDao;
    private final UserRepository userDao;

    public PostController(UserRepository userDao,  PostRepository postDao, CommentRepository commentDao, CommunityRepository communityDao) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.communityDao = communityDao;
    }
    @GetMapping("/discover")
    public String all(Model model) {
        List<Post> posts = postDao.findAll();
        User user;
        try{
            user = userDao.getReferenceById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        } catch (ClassCastException e){
            model.addAttribute("posts", posts);
            return "posts/discover";
        }
        Questionnaire questionnaire = user.getQuestionnaire();
        if(questionnaire.getNotifications().equals("Y")){
            model.addAttribute("message", Utility.getQuote(questionnaire));
        }
        model.addAttribute("friendList", Utility.getFriends(user));
        model.addAttribute("posts", posts);
        return "posts/discover";
    }
    @GetMapping("/forYou")
    public String showForYou(Model model){
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("user", new User());
        return "posts/forYou";

    }
    @GetMapping("/post/{id}/comments")
    public String showComments(@PathVariable long id, Model model){
        Post post = postDao.findById(id);
        List<Comment> comments = commentDao.findAllByPost(post);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new Comment());
        return "/posts/comments";

    }
    @PostMapping("/post/{id}/comments")
    public String addComment(@PathVariable long id, Model model, @ModelAttribute Comment comment) {
        Post post = postDao.findById(id);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(loggedInUser.getId());
        model.addAttribute("user", user);

        Date date = new Date();
        comment.setPost(post);
        comment.setUser(user);
        comment.setDate(date.toString());
        commentDao.save(comment);
        model.addAttribute("post", post);
        List<Comment> comments = commentDao.findAllByPost(post);

        model.addAttribute("comments", comments);
        model.addAttribute("comment", new Comment());

        return "/posts/comments";
    }
    @GetMapping("/post/create")
    public String getCreatePost(Model model) {
        User user = userDao.findById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        model.addAttribute("communities", user.getCommunities());
        model.addAttribute("post", new Post());
        return "/posts/create";
    }
    @GetMapping("/post/{postId}/edit")
    public String editPost(Model model, @PathVariable long postId){
        Post post = postDao.findById(postId);
        model.addAttribute("post", post);
        return "/posts/edit";
    }
    @PostMapping("/post/{postId}/edit")
    public String editPost(@PathVariable long postId, @ModelAttribute Post post){
        Post ogPost = postDao.findById(postId);
        ogPost.setTitle(post.getTitle());
        ogPost.setBody(post.getBody());
        postDao.save(ogPost);
        return "redirect:/profile";
    }
    @GetMapping("/post/{postId}/delete")
    public String deletePost(@PathVariable long postId){
        postDao.delete(postDao.getReferenceById(postId));
        return "redirect:/profile";
    }
    @GetMapping("/post/comment/{commentId}/delete")
    public String deleteComment(@PathVariable long commentId){
        commentDao.delete(commentDao.getReferenceById(commentId));
        return "redirect:/profile";
    }
    @PostMapping("/post/create")
    public String postCreatePost(@ModelAttribute Post post) {
        User user = userDao.findById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        Date date = new Date();
        List<Community> communities = new ArrayList<>();
        for (Community community : post.getCommunities()) {
            communities.add(communityDao.getReferenceById(community.getId()));
        }

        post.setCommunities(communities);
        post.setUser(user);
        post.setTime(date.toString());
        postDao.save(post);
        return "redirect:/profile";
    }
}