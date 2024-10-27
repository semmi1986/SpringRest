package com.itMentor.Task312.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "user_auth")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String lastName;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "link_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> setRoles = new ArrayList<>();

}
