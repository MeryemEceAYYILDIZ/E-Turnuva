package com.example.E_Turnuva.repository;

import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.entity.TournamentApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<TournamentApplication, Long> {
    boolean existsByTournamentAndTeam(Tournament tournament, Team team);
}