package com.itMentor.Task312.controller;

import com.itMentor.Task312.model.DTO.UserDTO;
import com.itMentor.Task312.model.User;
import com.itMentor.Task312.service.UserService;
import com.itMentor.Task312.model.Role;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;


    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public UserDTO getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());

        // Создаем объект UserDTO и заполняем его данными пользователя
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoleNames(user.getSetRoles().stream()
                .map(Role::getName)
                .toList());

        return userDTO;
    }
}
