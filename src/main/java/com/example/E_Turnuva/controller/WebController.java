package com.example.E_Turnuva.controller;

import com.example.E_Turnuva.entity.*;
import com.example.E_Turnuva.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {

    @Autowired private TournamentService tournamentService;
    @Autowired private GameService gameService;
    @Autowired private TeamService teamService;
    @Autowired private TeamMemberService teamMemberService;
    @Autowired private ApplicationService applicationService;
    @Autowired private MatchService matchService;

    // --- DASHBOARD ---
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalTournaments", tournamentService.getAllTournaments().size());
        model.addAttribute("totalTeams", teamService.getAllTeams().size());
        model.addAttribute("totalGames", gameService.getAllGames().size());
        return "dashboard";
    }

    // --- GAMES ---
    @GetMapping("/games")
    public String games(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("newGame", new Game());
        return "games";
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

    // --- TEAMS ---
    @GetMapping("/teams")
    public String teams(Model model) {
        model.addAttribute("teams", teamService.getAllTeams());
        model.addAttribute("newTeam", new Team());
        return "teams";
    }
    @PostMapping("/teams/add")
    public String addTeam(@ModelAttribute Team team) {
        teamService.createTeam(team);
        return "redirect:/teams";
    }
    @GetMapping("/teams/delete/{id}")
    public String deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return "redirect:/teams";
    }
    @GetMapping("/teams/{id}")
    public String teamDetails(@PathVariable Long id, Model model) {
        Team team = teamService.getAllTeams().stream().filter(t -> t.getId().equals(id)).findFirst().orElseThrow();
        model.addAttribute("team", team);
        model.addAttribute("newMember", new TeamMember());
        return "team-details";
    }
    @PostMapping("/teams/{teamId}/addMember")
    public String addMember(@PathVariable Long teamId, @ModelAttribute TeamMember member) {
        teamMemberService.addMemberToTeam(teamId, member.getPlayerName(), member.getRole());
        return "redirect:/teams/" + teamId;
    }
    @GetMapping("/members/delete/{teamId}/{memberId}")
    public String deleteMember(@PathVariable Long teamId, @PathVariable Long memberId) {
        teamMemberService.deleteMember(memberId);
        return "redirect:/teams/" + teamId;
    }

    // --- TOURNAMENTS ---
    @GetMapping("/tournaments")
    public String tournaments(Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("newTournament", new Tournament());
        return "tournaments";
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

    // Turnuva Düzenleme Sayfası
    @GetMapping("/tournaments/edit/{id}")
    public String editTournamentPage(@PathVariable Long id, Model model) {
        Tournament t = tournamentService.getTournamentById(id);
        model.addAttribute("tournament", t);
        model.addAttribute("games", gameService.getAllGames());
        return "tournament-edit";
    }

    @PostMapping("/tournaments/update/{id}")
    public String updateTournament(@PathVariable Long id, @ModelAttribute Tournament tournament) {
        tournamentService.updateTournament(id, tournament);
        return "redirect:/tournaments";
    }

    // --- APPLICATIONS (BAŞVURU) ---
    @GetMapping("/applications")
    public String applications(Model model) {
        model.addAttribute("applications", applicationService.getAllApplications());
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        model.addAttribute("teams", teamService.getAllTeams());
        return "applications";
    }

    @PostMapping("/applications/apply")
    public String applyToTournament(Long tournamentId, Long teamId, RedirectAttributes redirectAttributes) {
        try {
            applicationService.applyToTournament(tournamentId, teamId);
            redirectAttributes.addFlashAttribute("success", "Başvuru başarıyla alındı!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/applications";
    }

    @GetMapping("/applications/delete/{id}")
    public String deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return "redirect:/applications";
    }

    // --- MATCHES (EŞLEŞTİRME) ---
    @GetMapping("/matches")
    public String matches(Model model) {
        model.addAttribute("matches", matchService.getAllMatches());
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        model.addAttribute("teams", teamService.getAllTeams());
        return "matches";
    }

    @PostMapping("/matches/create")
    public String createMatch(Long tournamentId, Long team1Id, Long team2Id, String date, RedirectAttributes redirectAttributes) {
        try {
            matchService.createMatch(tournamentId, team1Id, team2Id, date);
            redirectAttributes.addFlashAttribute("success", "Maç başarıyla fikstüre eklendi.");
        } catch (RuntimeException e) {
            // Servisten gelen hatayı yakala ve ekrana bas
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/matches";
    }

    @GetMapping("/matches/delete/{id}")
    public String deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return "redirect:/matches";
    }
}