package com.codeup.Capstone_Communers.repositories;

import com.codeup.Capstone_Communers.models.Community;
import com.codeup.Capstone_Communers.models.Post;
import com.codeup.Capstone_Communers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);

    Post findById(long id);
    List<Post> findAllByUser(User user);

    @Query("from Post p where p.title like %:name%")
    List<Post> findLikeName(@Param("name") String name);

    @Query(value = "SELECT DISTINCT p.* FROM posts p " +
            "JOIN follows f ON f.followee = p.user_id " +
            "WHERE f.follower = :id " +
            "UNION " +
            "SELECT DISTINCT p.* FROM posts p " +
            "JOIN post_community pc ON p.id = pc.post_id " +
            "JOIN communities c ON pc.community_id = c.id " +
            "JOIN user_community uc ON c.id = uc.community_id " +
            "JOIN users u ON u.id = uc.user_id " +
            "WHERE u.id = :id AND NOT p.user_id = u.id " +
            "ORDER BY time", nativeQuery = true)
    List<Post> findPostsFromUserFollowsAndCommunities(long id);

    @Query("FROM Post p JOIN User u ON p.user.id = u.id WHERE u.id = :id ORDER BY p.time DESC")
    List<Post> findAllByUserId(@Param("id") long id);


    @Query("FROM Post p ORDER BY p.time DESC")
    List<Post> findPostsNewToOld();

}
