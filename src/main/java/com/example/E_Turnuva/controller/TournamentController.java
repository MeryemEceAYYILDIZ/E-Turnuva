package com.example.E_Turnuva.controller;

import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    // GET - Tüm turnuvaları listele
    // Adres: http://localhost:8080/api/tournaments
    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    // POST - Yeni turnuva ekle
    // Adres: http://localhost:8080/api/tournaments
    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        try {
            Tournament savedTournament = tournamentService.createTournament(tournament);
            return ResponseEntity.ok(savedTournament);
        } catch (IllegalArgumentException e) {
            // Eğer negatif ödül girilirse 400 Bad Request döndür
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long id, @RequestBody Tournament t) {
        return ResponseEntity.ok(tournamentService.updateTournament(id, t));
    }
}