package com.codeup.Capstone_Communers.repositories;

import com.codeup.Capstone_Communers.models.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Community getById(long id);

    Community findCommunitiesByName(String name);
}
