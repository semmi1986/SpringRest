package com.itMentor.Task312.service;

import com.itMentor.Task312.model.Role;
import com.itMentor.Task312.model.User;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role save(Role role);
    Role findByName(String name);
}
