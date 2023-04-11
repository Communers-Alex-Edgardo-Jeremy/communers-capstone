package com.codeup.Capstone_Communers.Services;

import com.codeup.Capstone_Communers.models.Community;
import com.codeup.Capstone_Communers.repositories.CommunityRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class Communities {
    private final CommunityRepository communityDAO;

    public Communities(CommunityRepository communityDAO) {
        this.communityDAO = communityDAO;
    }

    public Communities loadCommunityByName(String name) throws UsernameNotFoundException {
        Community community = communityDAO.findCommunitiesByName(name);
        if (community == null) {
            throw new UsernameNotFoundException("No Community found for " + communityDAO);
        }

        return (Communities) communityDAO;
    }
}
