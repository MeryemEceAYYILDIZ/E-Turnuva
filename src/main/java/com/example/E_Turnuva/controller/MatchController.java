package com.example.E_Turnuva.controller;

import com.example.E_Turnuva.entity.Match;
import com.example.E_Turnuva.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {
    @Autowired private MatchService matchService;

    // Örn: POST /api/matches?tournamentId=1&team1Id=2&team2Id=3&date=2026-06-01
    @PostMapping
    public Match createMatch(@RequestParam Long tournamentId,
                             @RequestParam Long team1Id,
                             @RequestParam Long team2Id,
                             @RequestParam String date) {
        return matchService.createMatch(tournamentId, team1Id, team2Id, date);
    }

    @GetMapping
    public List<Match> listAll() { return matchService.getAllMatches(); }
}