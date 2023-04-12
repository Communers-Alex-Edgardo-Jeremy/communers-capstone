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

                .defaultSuccessUrl("/discover", true) // user's home page, it can be any URL

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
                        "/post/{id}/edit", // only authenticated users can edit posts
                        "/post/{id}/comments", // only authenticated users can view comments
                        "/forYou",// only authenticated users can view all posts
                        "/profile",// only authenticated users can view their profile
                        "/settings"// only authenticated users can view their settings
                        ,"/journal"//only authenticated users can view their journal
                        , "/post/{postId}/delete"// only users can delete posts
                        , "/post/comment/{commentId}/delete" // only users can delete comments from their own posts
                        , "/user/edit" // only users can edit their profiles
                )
                .authenticated()

                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeHttpRequests()

                .requestMatchers("/", "/posts",  "/communities", "/register", "/discover", "/about", "/resources", "/css/**", "/js/**") // anyone can see home, the posts pages, and sign up

                .permitAll();
        return http.build();
    }

}
