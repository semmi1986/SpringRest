package com.itMentor.Task312.controller;

import com.itMentor.Task312.model.User;
import com.itMentor.Task312.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersResController {

    @Autowired
    private UserRepository userAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register/user")
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userAuthRepository.save(user);
    }

}
