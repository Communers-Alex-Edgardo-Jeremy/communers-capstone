package com.codeup.Capstone_Communers.controllers;

import com.codeup.Capstone_Communers.models.Post;
import com.codeup.Capstone_Communers.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.Capstone_Communers.models.User;
import com.codeup.Capstone_Communers.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostRepository postDao;

    @GetMapping("/discover")

    public String all(Model model) {
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
//        List<Post> somePosts = postDao.findLikeName("a");
        return "posts/discover";
    }

//    @GetMapping("/discover")
//    public String showPosts(Model model){
//        model.addAttribute("postList", postDao.findAll());
//        return "posts/discover";
//    }
    @GetMapping("/forYou")
    public String showForYou(Model model){
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
        return "posts/forYou";

    }
    @GetMapping("/post/create")
    public String getCreatePost(Model model) {
        model.addAttribute("post", new Post());
        return "/posts/create";
    }
    @GetMapping("/post/{postId}/edit")
    public String editPost(Model model, @PathVariable long postId){
        model.addAttribute("post", postDao.getReferenceById(postId));
        return "/post/edit";
    }

    @PostMapping("/post/{postId}/edit")
    public String editPost(@ModelAttribute Post post){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        post.setUser(user);
//        postDao.save(post);
        return "redirect:/users/profile";
    }

    @PostMapping("/posts/create")
    public String postCreatePost(@ModelAttribute Post post) {
//        List<Post> postList = new ArrayList<>();
//        post.setUser(userDao.findById(1));
//        emailService.prepareAndSend(post, post.getTitle(), post.getBody());
//        postDao.save(post);
        return "redirect:/users/profile";
    }

}