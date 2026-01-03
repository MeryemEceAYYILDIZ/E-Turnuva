package com.example.E_Turnuva.repository;

import com.example.E_Turnuva.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}