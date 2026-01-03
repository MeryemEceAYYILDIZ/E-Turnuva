package com.example.E_Turnuva.controller;

import com.example.E_Turnuva.entity.*;
import com.example.E_Turnuva.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @Autowired private TournamentService tournamentService;
    @Autowired private GameService gameService;
    @Autowired private TeamService teamService;
    @Autowired private TeamMemberService teamMemberService;

    // --- ANASAYFA (Dashboard) ---
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalTournaments", tournamentService.getAllTournaments().size());
        model.addAttribute("totalTeams", teamService.getAllTeams().size());
        model.addAttribute("totalGames", gameService.getAllGames().size());
        return "dashboard"; // dashboard.html
    }

    // --- OYUN (GAME) YÖNETİMİ ---
    @GetMapping("/games")
    public String games(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("newGame", new Game());
        return "games"; // games.html
    }

    @PostMapping("/games/add")
    public String addGame(@ModelAttribute Game game) {
        gameService.saveGame(game);
        return "redirect:/games";
    }

    @GetMapping("/games/delete/{id}")
    public String deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return "redirect:/games";
    }

    // --- TURNUVA YÖNETİMİ ---
    @GetMapping("/tournaments")
    public String tournaments(Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        model.addAttribute("games", gameService.getAllGames()); // Dropdown için oyunlar lazım
        model.addAttribute("newTournament", new Tournament());
        return "tournaments"; // tournaments.html (eski index.html'in gelişmişi)
    }

    @PostMapping("/tournaments/add")
    public String addTournament(@ModelAttribute Tournament tournament) {
        tournamentService.createTournament(tournament);
        return "redirect:/tournaments";
    }

    @GetMapping("/tournaments/delete/{id}")
    public String deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return "redirect:/tournaments";
    }

    // --- TAKIM (TEAM) YÖNETİMİ ---
    @GetMapping("/teams")
    public String teams(Model model) {
        model.addAttribute("teams", teamService.getAllTeams());
        model.addAttribute("newTeam", new Team());
        return "teams"; // teams.html
    }

    @PostMapping("/teams/add")
    public String addTeam(@ModelAttribute Team team) {
        teamService.createTeam(team);
        return "redirect:/teams";
    }

    // Takım Detay Sayfası (Üye Ekleme Burada Olacak)
    @GetMapping("/teams/{id}")
    public String teamDetails(@PathVariable Long id, Model model) {
        Team team = teamService.getAllTeams().stream().filter(t -> t.getId().equals(id)).findFirst().orElseThrow();
        model.addAttribute("team", team);
        model.addAttribute("newMember", new TeamMember());
        return "team-details"; // team-details.html
    }

    @PostMapping("/teams/{teamId}/addMember")
    public String addMember(@PathVariable Long teamId, @ModelAttribute TeamMember member) {
        // Formdan gelen 'playerName' ve 'role' değerlerini kullanacağız
        teamMemberService.addMemberToTeam(teamId, member.getPlayerName(), member.getRole());
        return "redirect:/teams/" + teamId;
    }

    @GetMapping("/members/delete/{teamId}/{memberId}")
    public String deleteMember(@PathVariable Long teamId, @PathVariable Long memberId) {
        teamMemberService.deleteMember(memberId);
        return "redirect:/teams/" + teamId;
    }
}