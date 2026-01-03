package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Match;
import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.repository.MatchRepository;
import com.example.E_Turnuva.repository.TeamRepository;
import com.example.E_Turnuva.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class MatchService {
    @Autowired private MatchRepository matchRepository;
    @Autowired private TournamentRepository tournamentRepository;
    @Autowired private TeamRepository teamRepository;

    // Yeni maç oluştur
    public Match createMatch(Long tournamentId, Long team1Id, Long team2Id, String date) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow();
        Team team1 = teamRepository.findById(team1Id).orElseThrow(); // teamRepository kullan
        Team team2 = teamRepository.findById(team2Id).orElseThrow();

        Match match = new Match();
        match.setTournament(tournament);
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setMatchDate(LocalDate.parse(date)); // Format: "2026-05-20"

        return matchRepository.save(match);
    }

    // Tüm maçları getir
    public List<Match> getAllMatches() { return matchRepository.findAll(); }
}