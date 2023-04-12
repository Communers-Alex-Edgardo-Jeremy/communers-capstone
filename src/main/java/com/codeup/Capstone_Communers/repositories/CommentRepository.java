package com.codeup.Capstone_Communers.repositories;

import com.codeup.Capstone_Communers.models.Comment;
import com.codeup.Capstone_Communers.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    Comment findById(long id);

    Comment findById(long id);
//    List <Comment> findAllByPost(long post_id);



    @Query("from Comment c where c.body like %:name%")
    List<Comment> findLikeName(@Param("name") String name);

}
