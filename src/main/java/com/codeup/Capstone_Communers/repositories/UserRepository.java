package com.codeup.Capstone_Communers.repositories;

import com.codeup.Capstone_Communers.models.Post;
import com.codeup.Capstone_Communers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    static User findByResetPasswordToken(String token) {
        return null;
    }

}