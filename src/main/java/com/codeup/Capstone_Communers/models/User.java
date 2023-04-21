package com.codeup.Capstone_Communers.models;

import com.codeup.Capstone_Communers.repositories.UserRepository;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length=255, nullable = false)
    private String first_name;

    @Column(length=255, nullable = false)
    private String last_name;

    @Column(length=255, nullable = false)
    private String username;

    @Column(length=255, nullable = false)
    private String email;

    @Column(length=255, nullable = false)
    private String password;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    //    The field verificationCode stores a random, unique String which is generated in the registration process and will be used in the verification process. Once registered, the enabled status of a user is false (disabled) so the user can’t login if he has not activated account by checking email and click on the verification link embedded in the email.
    //email send verification
    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    //email send verification
    @Column(nullable = false)
    private boolean enabled;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Questionnaire questionnaire;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Comment> comments;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name="user_community",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="community_id")}
    )
    private List <Community> communities;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name="follows",
            joinColumns={@JoinColumn(name="followee")},
            inverseJoinColumns={@JoinColumn(name="follower")}
    )
    private List <User> followers;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name="follows",
            joinColumns={@JoinColumn(name="follower")},
            inverseJoinColumns={@JoinColumn(name="followee")}
    )
    private List <User> followee;

    public User(User copy) {
        id = copy.id;
        email = copy.email;
        first_name = copy.first_name;
        last_name = copy.last_name;
        username = copy.username;
        password = copy.password;
    }

    //    method that needs to be placed inside of class that wraps a User object
    public boolean isEnabled() {
        return UserRepository.isEnabled();
    }

}