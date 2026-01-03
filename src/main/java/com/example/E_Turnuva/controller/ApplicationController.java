package com.example.E_Turnuva.controller;

import com.example.E_Turnuva.entity.TournamentApplication;
import com.example.E_Turnuva.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    @Autowired private ApplicationService applicationService;

    @PostMapping("/{tournamentId}/{teamId}")
    public TournamentApplication apply(@PathVariable Long tournamentId, @PathVariable Long teamId) {
        return applicationService.applyToTournament(tournamentId, teamId);
    }

    @GetMapping
    public List<TournamentApplication> listAll() {
        return applicationService.getAllApplications();
    }
}