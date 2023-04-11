package com.codeup.Capstone_Communers.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    private long user_id;

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

