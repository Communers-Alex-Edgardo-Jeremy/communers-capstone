package com.codeup.Capstone_Communers.models;
import jakarta.persistence.*;
import lombok.*;
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

    public User(User copy) {
        id = copy.id;
        email = copy.email;
        first_name = copy.first_name;
        last_name = copy.last_name;
        username = copy.username;
        password = copy.password;
    }
}