package com.itMentor.Task312.controller;

import com.itMentor.Task312.model.LoginRequest;
import com.itMentor.Task312.service.Impl.UserAuthDetailServiceImpl;
import com.itMentor.Task312.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserAuthDetailServiceImpl userAuthDetailService;
    private final JwtUtil jwtUtil; // Утилита для генерации JWT

    @Autowired
    public AuthController(UserAuthDetailServiceImpl userAuthDetailService,
                          JwtUtil jwtUtil) {
        this.userAuthDetailService = userAuthDetailService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Получение пользователя
            UserDetails userDetails = userAuthDetailService.loadUserByUsername(loginRequest.getUsername());
            // Генерация JWT токена
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(token);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found: " + e.getMessage());
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}

