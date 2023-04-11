package com.codeup.Capstone_Communers.controllers;

import ch.qos.logback.core.model.Model;
import com.codeup.Capstone_Communers.repositories.CommunityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CommunityController {

    private CommunityRepository communityDAO;

    public CommunityController(CommunityRepository communityDAO) {
        this.communityDAO = communityDAO;
    }

    @GetMapping("/communities")
    public String showCommunities(Model model){
        model.addText("communities");
        return "users/communities";
    }
}
