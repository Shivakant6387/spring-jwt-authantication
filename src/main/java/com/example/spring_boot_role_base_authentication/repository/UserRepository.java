package com.example.spring_boot_role_base_authentication.repository;

import com.example.spring_boot_role_base_authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
