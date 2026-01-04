package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Match;
import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.repository.ApplicationRepository;
import com.example.E_Turnuva.repository.MatchRepository;
import com.example.E_Turnuva.repository.TeamRepository;
import com.example.E_Turnuva.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class MatchService {

    @Autowired private MatchRepository matchRepository;
    @Autowired private TournamentRepository tournamentRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private ApplicationRepository applicationRepository;
    @Autowired private ApplicationService applicationService;

    public Match createMatch(Long tournamentId, Long team1Id, Long team2Id, String date) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow();
        LocalDate matchDate = LocalDate.parse(date);

        // KURAL 1: Geçmişe maç verilemez
        if (matchDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Hata: Geçmiş bir tarihe maç planlanamaz!");
        }

        // KURAL 2: Turnuva bitmişse maç eklenemez
        if ("FINISHED".equalsIgnoreCase(tournament.getStatus())) {
            throw new RuntimeException("Hata: Tamamlanmış bir turnuvaya maç ekleyemezsiniz!");
        }

        Team team1 = teamRepository.findById(team1Id).orElseThrow();
        Team team2 = teamRepository.findById(team2Id).orElseThrow();

        // KURAL 3: Başvuru kontrolü
        if (!applicationRepository.existsByTournamentAndTeam(tournament, team1)) {
            throw new RuntimeException("Hata: " + team1.getTeamName() + " takımı kayıtlı değil!");
        }
        if (!applicationRepository.existsByTournamentAndTeam(tournament, team2)) {
            throw new RuntimeException("Hata: " + team2.getTeamName() + " takımı kayıtlı değil!");
        }

        if (team1Id.equals(team2Id)) {
            throw new RuntimeException("Hata: Bir takım kendisiyle maç yapamaz!");
        }

        if (matchRepository.existsByTournamentAndTeam1AndTeam2AndMatchDate(tournament, team1, team2, matchDate)) {
            throw new RuntimeException("Hata: Bu maç zaten fikstürde mevcut!");
        }

        Match match = new Match();
        match.setTournament(tournament);
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setMatchDate(matchDate);

        Match savedMatch = matchRepository.save(match);

        // Takımların başvuru durumunu güncelle
        applicationService.updateStatus(tournament, team1, "ACCEPTED");
        applicationService.updateStatus(tournament, team2, "ACCEPTED");
        return savedMatch;
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    // YENİ: Maç İptal/Silme
    public void deleteMatch(Long id) {
        // 1. Silinecek maçı bul
        Match match = matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Maç bulunamadı"));

        // 2. Takımların başvuru durumunu tekrar "PENDING" (Bekliyor) yap
        // Böylece başvurular sayfasında "İptal Et" butonu tekrar görünür hale gelir.
        applicationService.updateStatus(match.getTournament(), match.getTeam1(), "PENDING");
        applicationService.updateStatus(match.getTournament(), match.getTeam2(), "PENDING");

        // 3. Maçı sil
        matchRepository.delete(match);
    }
}