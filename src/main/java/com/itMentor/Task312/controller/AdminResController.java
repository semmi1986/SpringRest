package com.itMentor.Task312.controller;

import com.itMentor.Task312.model.DTO.UserDTO;
import com.itMentor.Task312.model.Role;
import com.itMentor.Task312.model.User;
import com.itMentor.Task312.service.RoleService;
import com.itMentor.Task312.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminResController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public AdminResController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());

            // Получение имен ролей вместо идентификаторов
            List<String> roleNames = user.getSetRoles().stream()
                    .map(Role::getName) // Извлечение имен ролей
                    .collect(Collectors.toList());
            dto.setRoleNames(roleNames); // Установка имен ролей в DTO

            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Получение имен ролей вместо идентификаторов
        List<Role> roles = user.getSetRoles().stream()
                .map(role -> roleService.findByName(role.getName()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        user.setSetRoles(roles); // Устанавливаем найденные роли

        User savedUser = userService.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userService.findById(id);

        // Проверка, существует ли пользователь
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        // Обновление полей пользователя
        existingUser.setUsername(user.getUsername());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());

        // Проверка, изменился ли пароль
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Обновление ролей по названиям
        existingUser.getSetRoles().clear(); // Очищаем текущие роли
        List<Role> updatedRoles = user.getSetRoles().stream()
                .map(role -> roleService.findByName(role.getName())) // Находим роли по названию
                .filter(Objects::nonNull) // Фильтруем null значения, если роль не найдена
                .toList();
        existingUser.getSetRoles().addAll(updatedRoles); // Добавляем новые роли

        User updatedUser = userService.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/role")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        try {
            Role savedRole = roleService.save(role);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
