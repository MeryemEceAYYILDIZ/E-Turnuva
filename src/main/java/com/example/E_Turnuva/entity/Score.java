package com.example.E_Turnuva.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int team1Score;
    private int team2Score;
    private Long winnerTeamId;

    @OneToOne
    @JoinColumn(name = "match_id")
    private Match match;

    public Score() {
    }

    public Score(Long id, int team1Score, int team2Score, Long winnerTeamId, Match match) {
        this.id = id;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.winnerTeamId = winnerTeamId;
        this.match = match;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
    }

    public Long getWinnerTeamId() {
        return winnerTeamId;
    }

    public void setWinnerTeamId(Long winnerTeamId) {
        this.winnerTeamId = winnerTeamId;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}