package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.entity.TournamentApplication;
import com.example.E_Turnuva.repository.ApplicationRepository;
import com.example.E_Turnuva.repository.TournamentRepository;
import com.example.E_Turnuva.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired private ApplicationRepository applicationRepository;
    @Autowired private TournamentRepository tournamentRepository;
    @Autowired private TeamRepository teamRepository;

    // Başvuru Yap
    public TournamentApplication applyToTournament(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow();
        Team team = teamRepository.findById(teamId).orElseThrow();

        // 1. Turnuva durumu kontrolü
        if (!"OPEN".equalsIgnoreCase(tournament.getStatus())) {
            throw new RuntimeException("Bu turnuvanın kayıtları kapalıdır!");
        }

        // 2. Aynı takım, aynı turnuvaya bir daha başvuramaz
        if (applicationRepository.existsByTournamentAndTeam(tournament, team)) {
            throw new RuntimeException("Hata: Bu takım zaten bu turnuvaya başvurmuş!");
        }

        TournamentApplication application = new TournamentApplication();
        application.setTournament(tournament);
        application.setTeam(team);
        application.setStatus("PENDING");

        return applicationRepository.save(application);
    }

    // Başvuru Silme (İptal Etme)
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    // Başvuru Durumunu Güncelleme (Maç servisi kullanacak)
    public void updateStatus(Tournament tournament, Team team, String status) {
        // Bu turnuva ve takım için olan başvuruyu bul
        // (Bunun için Repository'ye yeni bir metot eklememiz gerekebilir, şimdilik stream ile bulalım)
        TournamentApplication app = applicationRepository.findAll().stream()
                .filter(a -> a.getTournament().getId().equals(tournament.getId()) && a.getTeam().getId().equals(team.getId()))
                .findFirst()
                .orElse(null); // Bulamazsa null dönsün, hata vermesin

        if (app != null) {
            app.setStatus(status);
            applicationRepository.save(app);
        }
    }

    // Başvuruları Listele
    public List<TournamentApplication> getAllApplications() {
        return applicationRepository.findAll();
    }
}