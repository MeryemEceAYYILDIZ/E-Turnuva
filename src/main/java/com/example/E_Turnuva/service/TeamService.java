package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }
}