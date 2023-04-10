package com.codeup.Capstone_Communers.models;

import jakarta.persistence.*;
import lombok.*;

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

    public Community(Community copy) {
        id = copy.id;
        name = copy.name;
        bio = copy.bio;

    }
}
