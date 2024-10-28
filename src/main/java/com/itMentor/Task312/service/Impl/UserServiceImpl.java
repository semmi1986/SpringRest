package com.itMentor.Task312.service.Impl;

import com.itMentor.Task312.model.User;
import com.itMentor.Task312.repositories.UserRepository;
import com.itMentor.Task312.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        repository.save(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found: " + id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
       return repository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Role not found: " + username));
    }




}
