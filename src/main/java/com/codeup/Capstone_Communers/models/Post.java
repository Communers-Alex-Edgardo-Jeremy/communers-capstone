package com.codeup.Capstone_Communers.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String time;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

//    @ManyToOne
//    @JoinColumn (name = "comment_id")
//    private Comment comment;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
//    List<Comment> tasks = new ArrayList<>();

//    public Comment() {
//    }

//    public Comment(String body) {
//        this.body = body;
//    }

//    public List<Comment> getTasks() {
//        return comments;
//    }

}

