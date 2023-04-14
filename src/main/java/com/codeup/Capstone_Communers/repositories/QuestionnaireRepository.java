package com.codeup.Capstone_Communers.repositories;

import com.codeup.Capstone_Communers.models.Post;
import com.codeup.Capstone_Communers.models.Questionnaire;
import com.codeup.Capstone_Communers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

//    List<Questionnaire> findByQuestion(String title);
    Questionnaire findById(long id);
    List<Questionnaire> findAllByUser(User user);

//    @Query("from Questionnaire q where q.question like %:name%")
//    List<Questionnaire> findLikeName(@Param("name") String name);

}
