package com.example.E_Turnuva.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private Double prizePool;
    private String status; // "OPEN", "ACTIVE", "FINISHED"

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public Tournament() {
    }

    public Tournament(Long id, String name, LocalDate startDate, Double prizePool, String status, Game game) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.prizePool = prizePool;
        this.status = status;
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(Double prizePool) {
        this.prizePool = prizePool;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}