package com.codeup.Capstone_Communers.models;
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

//    reset password
    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
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

}