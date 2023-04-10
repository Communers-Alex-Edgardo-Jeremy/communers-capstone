package com.codeup.Capstone_Communers.controllers;

import com.codeup.Capstone_Communers.models.Community;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class CommunityController {

    @RequestMapping("/communities")
    public Community getCommunity(@RequestParam(name = "communities", required = false, defaultValue = "Unknown") String name , String bio) {
        Community community = new Community();
        community.setName(name);
        community.setBio(bio);
        return community;
    }
}
