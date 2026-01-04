package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Match;
import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.repository.ApplicationRepository;
import com.example.E_Turnuva.repository.MatchRepository;
import com.example.E_Turnuva.repository.TeamRepository;
import com.example.E_Turnuva.repository.TournamentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @Mock private MatchRepository matchRepository;
    @Mock private TournamentRepository tournamentRepository;
    @Mock private TeamRepository teamRepository;
    @Mock private ApplicationRepository applicationRepository;
    @Mock private ApplicationService applicationService; // MatchService içinde bu servisi kullanıyoruz

    @InjectMocks private MatchService matchService;

    // --- SENARYO 7: Kayıtlı Olmayan Takım Maçı ---
    @Test
    public void testUnregisteredTeamMatch() {
        Tournament t = new Tournament(); t.setId(1L); t.setStatus("ACTIVE");
        Team t1 = new Team(); t1.setId(1L); t1.setTeamName("A Takımı");
        Team t2 = new Team(); t2.setId(2L); t2.setTeamName("B Takımı");

        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(t));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(t1));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(t2));

        // A Takımı kayıtlı değil (false)
        when(applicationRepository.existsByTournamentAndTeam(t, t1)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            matchService.createMatch(1L, 1L, 2L, "2030-01-01");
        });

        assertTrue(exception.getMessage().contains("takımı kayıtlı değil"));
    }

    // --- SENARYO 8: Maç İptali ve Durum Geri Alma ---
    @Test
    public void testDeleteMatchRestoresStatus() {
        Tournament t = new Tournament();
        Team t1 = new Team();
        Team t2 = new Team();
        Match match = new Match();
        match.setId(100L);
        match.setTournament(t);
        match.setTeam1(t1);
        match.setTeam2(t2);

        when(matchRepository.findById(100L)).thenReturn(Optional.of(match));

        matchService.deleteMatch(100L);

        // ApplicationService'in çağrıldığını doğrula (Verify)
        verify(applicationService, times(1)).updateStatus(t, t1, "PENDING");
        verify(applicationService, times(1)).updateStatus(t, t2, "PENDING");
        verify(matchRepository, times(1)).delete(match);
    }

    // --- SENARYO 9: Kendi Kendine Maç ---
    @Test
    public void testSelfMatch() {
        Tournament t = new Tournament(); t.setId(1L);
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(t));

        // Mocklama sırasına gerek kalmadan exception fırlatılmalı çünkü ID kontrolü repository'den sonra
        // Ama repository findById çağrıldığı için onu mocklamalıyız.
        when(teamRepository.findById(1L)).thenReturn(Optional.of(new Team()));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            matchService.createMatch(1L, 1L, 1L, "2030-01-01");
        });

        assertEquals("Hata: Bir takım kendisiyle maç yapamaz!", exception.getMessage());
    }

    // --- SENARYO 6: Maç Fikstürü Oluşturma (Happy Path) ---
    @Test
    public void testCreateMatch_HappyPath() {
        // 1. Hazırlık (Mock Veriler)
        Tournament t = new Tournament(); t.setId(1L); t.setStatus("OPEN");
        Team t1 = new Team(); t1.setId(1L); t1.setTeamName("T1");
        Team t2 = new Team(); t2.setId(2L); t2.setTeamName("G2");

        // Repository çağrıldığında bu sahte verileri dön
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(t));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(t1));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(t2));

        // Kuralları geçmesi için gerekli koşulları sağla
        // Takımlar kayıtlı mı? EVET
        when(applicationRepository.existsByTournamentAndTeam(t, t1)).thenReturn(true);
        when(applicationRepository.existsByTournamentAndTeam(t, t2)).thenReturn(true);
        // Böyle bir maç zaten var mı? HAYIR (Yoksa kaydedemeyiz)
        when(matchRepository.existsByTournamentAndTeam1AndTeam2AndMatchDate(any(), any(), any(), any())).thenReturn(false);

        // Kayıt sonrası dönülecek maç nesnesi
        Match savedMatch = new Match();
        savedMatch.setId(100L);
        when(matchRepository.save(any(Match.class))).thenReturn(savedMatch);

        // 2. İşlem (Service metodunu çağır)
        matchService.createMatch(1L, 1L, 2L, "2026-05-20");

        // 3. Doğrulama (Verify)
        // Maç kaydedildi mi?
        verify(matchRepository, times(1)).save(any(Match.class));

        // KRİTİK KONTROL: Takımların durumu "ACCEPTED" yapıldı mı?
        verify(applicationService, times(1)).updateStatus(t, t1, "ACCEPTED");
        verify(applicationService, times(1)).updateStatus(t, t2, "ACCEPTED");
    }
}