package com.example.Demo_E_Commerce.repository;

import com.example.Demo_E_Commerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users , Integer> {
    Optional<Users> findByEmail(String email);
}
