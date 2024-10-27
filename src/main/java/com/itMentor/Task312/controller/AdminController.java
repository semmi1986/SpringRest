package com.itMentor.Task312.controller;

import com.itMentor.Task312.model.Role;
import com.itMentor.Task312.model.User;
import com.itMentor.Task312.service.Impl.RoleServiceImpl;
import com.itMentor.Task312.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleServiceImpl roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }
    //    @GetMapping("/users/showUserAuth")
//    public String showUserAuth() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserAuthDetails userAuthDetails = (UserAuthDetails) authentication.getPrincipal();
//        System.out.println(userAuthDetails.getUserAuth());
//        return "/auth/users";
//    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable("id") long id, Model model) {
        User userAuth = userService.findById(id);
        model.addAttribute("user", userAuth);
        return "userInfo";
    }

    @GetMapping(value = "/user/edit/{id}")
    public String editUser(ModelMap model, @PathVariable("id") Long id) {
        User user = userService.findById(id);

        // Получение списка всех ролей
        List<Role> roles = roleService.findAll();

        // Добавление пользователя и ролей в модель
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "editUser";
    }

    @PostMapping("/user/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("user") User user) {
        // Получение существующего пользователя из базы данных
        User existingUser = userService.findById(id);

        // Обновление полей пользователя
        existingUser.setUsername(user.getUsername());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        // Проверка, изменился ли пароль
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // Если пароль изменился, шифруем и обновляем его
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Обновляем роли
        existingUser.setSetRoles(user.getSetRoles());

        userService.save(existingUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "addUser";
    }

    @PostMapping("/user")
    public String addUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        userService.delete(user.getId());
        return "redirect:/admin/users";
    }

}
