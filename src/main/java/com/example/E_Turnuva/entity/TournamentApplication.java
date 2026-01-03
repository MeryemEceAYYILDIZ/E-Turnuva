package com.example.E_Turnuva.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class TournamentApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status; // "PENDING", "APPROVED"

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public TournamentApplication() {
    }

    public TournamentApplication(Long id, String status, Tournament tournament, Team team) {
        this.id = id;
        this.status = status;
        this.tournament = tournament;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}