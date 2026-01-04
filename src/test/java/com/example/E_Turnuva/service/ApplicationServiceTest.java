package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Team;
import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.repository.ApplicationRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock private ApplicationRepository applicationRepository;
    @Mock private TournamentRepository tournamentRepository;
    @Mock private TeamRepository teamRepository;

    @InjectMocks private ApplicationService applicationService;

    // --- SENARYO 4: Kapalı Turnuvaya Başvuru ---
    @Test
    public void testApplicationToClosedTournament() {
        Tournament t = new Tournament(); t.setId(1L); t.setStatus("FINISHED");
        Team team = new Team(); team.setId(1L);

        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(t));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            applicationService.applyToTournament(1L, 1L);
        });

        assertEquals("Bu turnuvanın kayıtları kapalıdır!", exception.getMessage());
    }

    // --- SENARYO 5: Mükerrer Başvuru Kontrolü ---
    @Test
    public void testDuplicateApplication() {
        Tournament t = new Tournament(); t.setId(1L); t.setStatus("OPEN");
        Team team = new Team(); team.setId(1L);

        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(t));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(applicationRepository.existsByTournamentAndTeam(t, team)).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            applicationService.applyToTournament(1L, 1L);
        });

        assertEquals("Hata: Bu takım zaten bu turnuvaya başvurmuş!", exception.getMessage());
    }
}