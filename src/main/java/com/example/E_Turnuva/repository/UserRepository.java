package com.example.E_Turnuva.repository;

import com.example.E_Turnuva.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Özel sorgu gerekirse buraya yazarız (Örn: username ile bul)
    User findByUsername(String username);
}