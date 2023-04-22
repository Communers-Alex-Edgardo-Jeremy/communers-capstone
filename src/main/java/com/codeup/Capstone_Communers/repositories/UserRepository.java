package com.codeup.Capstone_Communers.repositories;

import com.codeup.Capstone_Communers.models.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    User findByUsername(String username);

    @NotNull List<User> findAll();


    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    User findByResetPasswordToken(String token);

    static boolean isEnabled() {
        return false;
    }

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    User findByVerificationCode(String code);

}

