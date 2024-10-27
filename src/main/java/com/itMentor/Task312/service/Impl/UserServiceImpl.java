package com.itMentor.Task312.service.Impl;

import com.itMentor.Task312.model.User;
import com.itMentor.Task312.repositories.UserRepository;
import com.itMentor.Task312.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public User findById(Long id) {
        Optional<User> foundPerson = repository.findById(id);
        return foundPerson.get();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> foundPerson = repository.findByUsername(username);
        return foundPerson.get();
    }


}
