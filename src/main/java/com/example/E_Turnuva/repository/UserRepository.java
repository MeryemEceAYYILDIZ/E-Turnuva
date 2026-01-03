package com.example.E_Turnuva.repository;

import com.example.E_Turnuva.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}