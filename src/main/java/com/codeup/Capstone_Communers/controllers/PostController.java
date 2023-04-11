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

@Controller
@AllArgsConstructor
public class PostController {

    private final PostRepository postDao;


    @GetMapping("/post/create")
    public String returnCreateView(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @GetMapping("/post/edit")
    public String returnEditView(@ModelAttribute Post post) {
        postDao.save(post);
        return "posts/edit";
    }

}