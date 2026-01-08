package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    // ID ile turnuva bul
    public Tournament getTournamentById(Long id) {
        return tournamentRepository.findById(id).orElseThrow();
    }

    // Tüm turnuvaları getir
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    // Turnuvaya Takım Ekleme (İlişkisel İşlem)
    public void addTeamToTournament(Long tournamentId, Long teamId) {
        System.out.println(teamId + " nolu takım " + tournamentId + " nolu turnuvaya eklendi.");
    }

    // Yeni turnuva kaydet
    public Tournament createTournament(Tournament tournament) {
        // Eğer ödül havuzu negatif girilirse kaydetme, hata fırlat.
        if (tournament.getPrizePool() != null && tournament.getPrizePool() < 0) {
            throw new IllegalArgumentException("Ödül havuzu negatif olamaz!");
        }
        return tournamentRepository.save(tournament);
    }

    // Turnuva Güncelle
    public Tournament updateTournament(Long id, Tournament updatedTournament) {
        Tournament existing = tournamentRepository.findById(id).orElseThrow();

        existing.setName(updatedTournament.getName());
        existing.setPrizePool(updatedTournament.getPrizePool());
        existing.setStatus(updatedTournament.getStatus());
        existing.setGame(updatedTournament.getGame());

        return tournamentRepository.save(existing); // Kaydedilen nesneyi geri dön
    }

    // Turnuva Sil
    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }
}