package com.codeup.Capstone_Communers.controllers;

import com.codeup.Capstone_Communers.models.Comment;
import com.codeup.Capstone_Communers.models.Entry;
import com.codeup.Capstone_Communers.models.Post;
import com.codeup.Capstone_Communers.repositories.CommentRepository;
import com.codeup.Capstone_Communers.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.Capstone_Communers.models.User;
import com.codeup.Capstone_Communers.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final CommentRepository commentDao;
    private final UserRepository userDao;


    public PostController(UserRepository userDao,  PostRepository postDao, CommentRepository commentDao) {

        this.userDao = userDao;
        this.postDao = postDao;
        this.commentDao = commentDao;
    }

    @GetMapping("/discover")
    public String all(Model model) {
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
//        List<Post> somePosts = postDao.findLikeName("a");
        return "posts/discover";
    }

    @GetMapping("/forYou")
    public String showForYou(Model model){
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
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
        model.addAttribute("post", new Post());
        return "/posts/create";
    }
    @GetMapping("/post/{postId}/edit")
    public String editPost(Model model, @PathVariable long postId){
        model.addAttribute("post", postDao.getReferenceById(postId));
        return "/posts/edit";
    }

    @PostMapping("/post/{postId}/edit")
    public String editPost(@PathVariable long postId, @ModelAttribute Post editedpost){
        User user = userDao.findById(postId);
        editedpost.setUser(user);
        postDao.save(editedpost);
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date date = new Date();
        String stringDate = date.toString();
        post.setUser(user);
        post.setTime(stringDate);
        postDao.save(post);
        return "redirect:/profile";
    }

}