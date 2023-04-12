package com.codeup.Capstone_Communers.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String date;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

//    @ToString.Exclude
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "entry")
//    private List<Comment> entries;
}

