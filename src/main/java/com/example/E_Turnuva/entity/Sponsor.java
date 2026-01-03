package com.example.E_Turnuva.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sponsors")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private Double sponsorshipAmount;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    public Sponsor() {
    }

    public Sponsor(Long id, String companyName, Double sponsorshipAmount, Tournament tournament) {
        this.id = id;
        this.companyName = companyName;
        this.sponsorshipAmount = sponsorshipAmount;
        this.tournament = tournament;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getSponsorshipAmount() {
        return sponsorshipAmount;
    }

    public void setSponsorshipAmount(Double sponsorshipAmount) {
        this.sponsorshipAmount = sponsorshipAmount;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}