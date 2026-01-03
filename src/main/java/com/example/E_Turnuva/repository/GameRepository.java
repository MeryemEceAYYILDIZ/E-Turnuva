package com.example.E_Turnuva.repository;

import com.example.E_Turnuva.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}