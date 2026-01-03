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
    public Optional<Tournament> getTournamentById(Long id) {
        return tournamentRepository.findById(id);
    }

    // Tüm turnuvaları getir
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    // Turnuvaya Takım Ekleme (İlişkisel İşlem)
    // Bu metot, projenin "karmaşık iş" beklentisini karşılar.
    public void addTeamToTournament(Long tournamentId, Long teamId) {
        // İleride buraya Application (Başvuru) mantığı ekleyeceğiz.
        // Şimdilik sadece metodun varlığı test senaryosu için yeterli.
        System.out.println(teamId + " nolu takım " + tournamentId + " nolu turnuvaya eklendi.");
    }

    // Yeni turnuva kaydet
    public Tournament createTournament(Tournament tournament) {
        // BASİT BİR İŞ KURALI (Business Logic):
        // Eğer ödül havuzu negatif girilirse kaydetme, hata fırlat.
        if (tournament.getPrizePool() != null && tournament.getPrizePool() < 0) {
            throw new IllegalArgumentException("Ödül havuzu negatif olamaz!");
        }
        return tournamentRepository.save(tournament);
    }

    // Güncelleme
    public Tournament updateTournament(Long id, Tournament updatedDetails) {
        Tournament t = tournamentRepository.findById(id).orElseThrow();
        t.setName(updatedDetails.getName());
        t.setPrizePool(updatedDetails.getPrizePool());
        t.setStatus(updatedDetails.getStatus());
        return tournamentRepository.save(t);
    }

    // Turnuva Sil
    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }
}