package com.example.spring_boot_role_base_authentication.service;

import com.example.spring_boot_role_base_authentication.config.JwtService;
import com.example.spring_boot_role_base_authentication.dto.JwtResponse;
import com.example.spring_boot_role_base_authentication.dto.LoginRequest;
import com.example.spring_boot_role_base_authentication.dto.RegisterRequest;
import com.example.spring_boot_role_base_authentication.model.Role;
import com.example.spring_boot_role_base_authentication.model.User;
import com.example.spring_boot_role_base_authentication.repository.RoleRepository;
import com.example.spring_boot_role_base_authentication.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

//    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtService = jwtService;
//        this.authenticationManager = authenticationManager;
//    }

    public void register(RegisterRequest request,String roleName) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = roleRepository.findByName(roleName);
        user.setRoles(Set.of(role));
        userRepository.save(user);
    }


    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtService.generateToken(request.getUsername());
        return new JwtResponse(token);
    }
}
