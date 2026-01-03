package com.example.E_Turnuva.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gameName;
    private String genre; // FPS, MOBA

    @OneToMany(mappedBy = "game")
    private List<Tournament> tournaments;

    public Game() {
    }

    public Game(String gameName, List<Tournament> tournaments, String genre, Long id) {
        this.gameName = gameName;
        this.tournaments = tournaments;
        this.genre = genre;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}