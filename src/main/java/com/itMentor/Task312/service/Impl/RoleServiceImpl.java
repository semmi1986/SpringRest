package com.itMentor.Task312.service.Impl;

import com.itMentor.Task312.model.Role;
import com.itMentor.Task312.repositories.RoleRepository;
import com.itMentor.Task312.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.repository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

}
