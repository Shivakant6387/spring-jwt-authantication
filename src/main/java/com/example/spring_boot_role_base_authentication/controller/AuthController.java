package com.example.spring_boot_role_base_authentication.controller;

import com.example.spring_boot_role_base_authentication.dto.JwtResponse;
import com.example.spring_boot_role_base_authentication.dto.LoginRequest;
import com.example.spring_boot_role_base_authentication.dto.RegisterRequest;
import com.example.spring_boot_role_base_authentication.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
