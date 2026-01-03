package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.entity.TournamentApplication; // Entity ismini kontrol et
import com.example.E_Turnuva.repository.ApplicationRepository; // Repository'yi oluşturman gerekecek (aşağıda)
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

        TournamentApplication application = new TournamentApplication();
        application.setTournament(tournament);
        application.setTeam(team);
        application.setStatus("PENDING"); // Onay bekliyor

        return applicationRepository.save(application);
    }

    // Başvuruları Listele
    public List<TournamentApplication> getAllApplications() {
        return applicationRepository.findAll();
    }
}