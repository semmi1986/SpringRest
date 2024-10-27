package com.itMentor.Task312.service;

import com.itMentor.Task312.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    void save(User user);
    void delete(Long id);
    User findByUsername(String username);

}
