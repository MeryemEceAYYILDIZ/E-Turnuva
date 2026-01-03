package com.example.E_Turnuva.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    private String tag; // Örn: T1, FB, GS

    @OneToMany(mappedBy = "team")
    private List<TeamMember> members;

    public Team() {
    }

    public Team(Long id, String teamName, String tag, List<TeamMember> members) {
        this.id = id;
        this.teamName = teamName;
        this.tag = tag;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }
}