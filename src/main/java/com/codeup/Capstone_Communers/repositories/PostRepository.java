package com.codeup.Capstone_Communers.repositories;

import com.codeup.Capstone_Communers.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);
    Post findById(long id);

    @Query("from Post p where p.title like %:name%")
    List<Post> findLikeName(@Param("name") String name);

}
