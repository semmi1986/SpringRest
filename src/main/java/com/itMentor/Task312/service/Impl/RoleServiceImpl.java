package com.itMentor.Task312.service.Impl;

import com.itMentor.Task312.model.Role;
import com.itMentor.Task312.repositories.RoleRepository;
import com.itMentor.Task312.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public Role findByName(String name) {
        Optional<Role> role = repository.findByName(name);
        return role.get();
    }

}
