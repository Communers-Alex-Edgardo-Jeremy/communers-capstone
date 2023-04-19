package com.codeup.Capstone_Communers;

import com.codeup.Capstone_Communers.Services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
                .formLogin()
                .loginPage("/login")

                //        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                .defaultSuccessUrl("/questionnaire", true) // user's home page, it can be any URL

                .permitAll() // Anyone can go to the login page

                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/login") // append a query string value

                /* Pages that require authentication */
                .and()
                .authorizeHttpRequests()
                .requestMatchers(

                        "/post/create", // only authenticated users can create posts
                        "/post/{postId}/edit", // only authenticated users can edit posts
                        "/post/{id}/comments", // only authenticated users can view comments
                        "/forYou",// only authenticated users can view all posts
                        "/questionnaire",// only authenticated users can submit a questionnaire
                        "/profile",// only authenticated users can view their profile
                        "/settings",// only authenticated users can view their settings
                        "/chats",// only authenticated users can view their chats
                        "/chat/{userId}", // only authenticated users can view their chat
                        "/loggedInChatUser",
                        "/journal",//only authenticated users can view their journal
                        "/journal/addEntry",//only authenticated users can edit their journal
                        "/journal/{entryId}/edit",// only users can edit entries
                        "/journal/{entryId}/delete",// only users can delete entries
                        "/post/{postId}/delete",// only users can delete posts
                        "/post/comment/{commentId}/delete", // only users can delete comments from their own posts
                        "/user/edit", // only users can edit their profiles
                        "/user/delete", //only users can delete their account
                        "/follow/{userId}", //only users can follow other users
                        "/communities", // only users can browse their communities
                        "/community/{communityId}", // only users can view specific communities
                        "/communities/discover", //only users can browse communities
                        "/find/user", // only users can find other users
                        "/updateCheckbox" // only users can update their notification settings
                )
                .authenticated()

                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeHttpRequests()
                

                .requestMatchers("/", "/landing", "/posts","/forgot_password","/reset_password", "/register", "/discover", "/about", "/resources", "/css/**", "/js/**", "/img/**") // anyone can see home, the posts pages, and sign up



                .permitAll();
        return http.build();
    }

}
