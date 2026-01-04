package com.example.E_Turnuva.repository;

import com.example.E_Turnuva.entity.Match;
import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface MatchRepository extends JpaRepository<Match, Long> {
    // Bu turnuvada, bu iki takımın, bu tarihte maçı var mı?
    boolean existsByTournamentAndTeam1AndTeam2AndMatchDate(Tournament tournament, Team team1, Team team2, LocalDate matchDate);
}
