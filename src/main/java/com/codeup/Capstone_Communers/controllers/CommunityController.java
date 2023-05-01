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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        User user = userDao.getReferenceById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        Community community = communityDao.getReferenceById(communityId);
        model.addAttribute("loggedInUser", user);
        model.addAttribute("communityPosts", community.getPosts());
        model.addAttribute("community", community);
        return "/communities/community";
    }
//    @PostMapping("/community/{communityId}")
//    public String followCommunity(@PathVariable long communityId, Model model){
//        Community community = communityDao.getReferenceById(communityId);
//        List<User> followers = community.getUsers();
//        followers.add((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        community.setUsers(followers);
//        communityDao.save(community);
//        model.addAttribute("communityPosts", community.getPosts());
//        model.addAttribute("community", community);
//        return "/communities/community";
//    }

    @PostMapping("/community")
    @ResponseBody
    public Map<String, Object> followCommunity(@RequestBody Community partialCommunity) {
        System.out.println(partialCommunity.getId());
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User follower = userDao.getReferenceById(loggedInUser.getId());
        Community community = communityDao.getReferenceById(partialCommunity.getId());

        Map<String, Object> response = new HashMap<>();

        // check if the follower is already following the followee
        boolean isFollowing = community.getUsers().contains(follower);


        // update the follower-followee relationship in the database
        if (!isFollowing) {
            System.out.println("saving user");
            community.getUsers().add(follower);
            communityDao.save(community);
        }
        if (isFollowing) {
            System.out.println("unsaving user");
            community.getUsers().remove(follower);
            communityDao.save(community);
        }
        System.out.println("before isfollowing check" + isFollowing);
        System.out.println(follower.getId());
        System.out.println(community.getId());
        response.put("following", isFollowing);


        return response;
    }



    @GetMapping("/communities")
    public String showCommunities(Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User fullUser = userDao.getReferenceById(loggedInUser.getId());
        model.addAttribute("loggedInUser", fullUser);
        model.addAttribute("communities", fullUser.getCommunities());
        return "/communities/communities";
    }

    @GetMapping("/communities/discover")
    public String allCommunities(Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User fullUser = userDao.getReferenceById(loggedInUser.getId());
        model.addAttribute("loggedInUser", fullUser);
        model.addAttribute("communities", communityDao.findAll());
        return "/communities/discover";
    }

}
