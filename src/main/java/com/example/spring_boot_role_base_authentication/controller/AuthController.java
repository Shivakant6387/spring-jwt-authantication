package com.example.spring_boot_role_base_authentication.controller;

import com.example.spring_boot_role_base_authentication.dto.JwtResponse;
import com.example.spring_boot_role_base_authentication.dto.LoginRequest;
import com.example.spring_boot_role_base_authentication.dto.RegisterRequest;
import com.example.spring_boot_role_base_authentication.exception.TokenGenerationException;
import com.example.spring_boot_role_base_authentication.exception.UserNotFoundException;
import com.example.spring_boot_role_base_authentication.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request, @RequestParam String roleName) {
        authService.register(request, roleName);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/generate-token/{userId}")
    public ResponseEntity<JwtResponse> generateTokenByUserId(@PathVariable long userId) {
        try {
            JwtResponse jwtResponse = authService.generateTokenByUserId(userId);
            return ResponseEntity.ok(jwtResponse);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(404).body(new JwtResponse(0, "User Not Found", null, ""));
        } catch (TokenGenerationException ex) {
            return ResponseEntity.status(500).body(new JwtResponse(0, "Error generating token", null, ""));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new JwtResponse(0, "Internal server error", null, ""));
        }
    }
}
