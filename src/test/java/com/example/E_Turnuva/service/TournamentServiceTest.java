package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.repository.TournamentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TournamentServiceTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private TournamentService tournamentService;

    @Test
    public void testCreateTournament_Success() {
        // SENARYO 1: Her şey yolunda, turnuva kaydedilmeli.
        Tournament tournament = new Tournament();
        tournament.setName("LoL Kış Turnuvası");
        tournament.setPrizePool(1000.0);

        Mockito.when(tournamentRepository.save(any(Tournament.class))).thenReturn(tournament);

        Tournament created = tournamentService.createTournament(tournament);

        Assertions.assertNotNull(created);
        Assertions.assertEquals("LoL Kış Turnuvası", created.getName());
    }

    @Test
    public void testCreateTournament_NegativePrize_ShouldThrowException() {
        // SENARYO 2: Ödül havuzu negatif girilirse hata vermeli.

        Tournament tournament = new Tournament();
        tournament.setName("Hatalı Turnuva");
        tournament.setPrizePool(-500.0); // Hatalı veri

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tournamentService.createTournament(tournament);
        });
    }
}