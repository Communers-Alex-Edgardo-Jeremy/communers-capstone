package com.codeup.Capstone_Communers.controllers;

import com.codeup.Capstone_Communers.models.Community;
import com.codeup.Capstone_Communers.models.Post;
import com.codeup.Capstone_Communers.models.User;
import com.codeup.Capstone_Communers.repositories.CommentRepository;
import com.codeup.Capstone_Communers.repositories.CommunityRepository;
import com.codeup.Capstone_Communers.repositories.PostRepository;
import com.codeup.Capstone_Communers.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class CommunityController {

    private final PostRepository postDao;
    private final CommentRepository commentDao;
    private final UserRepository userDao;
    private final CommunityRepository communityDao;

    public CommunityController(PostRepository postDao, CommentRepository commentDao, UserRepository userDao, CommunityRepository communityDao) {
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.userDao = userDao;
        this.communityDao = communityDao;
    }

    @GetMapping("/community/{communityId}")
    public String showCommunity(@PathVariable long communityId, Model model){
        Community community = communityDao.getReferenceById(communityId);
        model.addAttribute("communityPosts", community.getPosts());
        model.addAttribute("community", community);
        return "/communities/community";
    }
    @PostMapping("/community/{communityId}")
    public String followCommunity(@PathVariable long communityId, Model model){
        Community community = communityDao.getReferenceById(communityId);
        List<User> followers = community.getUsers();
        followers.add((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        community.setUsers(followers);
        communityDao.save(community);
        model.addAttribute("communityPosts", community.getPosts());
        model.addAttribute("community", community);
        return "/communities/community";
    }


    @GetMapping("/communities")
    public String showCommunities(Model model){
        model.addAttribute("communities", communityDao.findAll());
        return "/communities/discover";
    }
}
