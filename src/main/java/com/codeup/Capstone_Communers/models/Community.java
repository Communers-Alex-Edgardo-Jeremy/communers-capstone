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
@Table(name = "communities")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length=100, nullable = false)
    private String name;

    @Column(length=255, nullable = false)
    private String bio;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "communities")
    private List<Post> posts;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name="user_community",
            joinColumns={@JoinColumn(name="community_id")},
            inverseJoinColumns={@JoinColumn(name="user_id")}
    )
    private List <User> users;


    public Community(Community copy) {
        id = copy.id;
        name = copy.name;
        bio = copy.bio;
    }
}
