package com.codeup.Capstone_Communers.repositories;

import com.codeup.Capstone_Communers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    User findByUsername(String username);


//    @Query("FROM User u JOIN follows f ON u.id = f.follower WHERE u.id = :id")
//    List<User> findFollowing(long id);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail (String email);

    User findByResetPasswordToken(String token);


}