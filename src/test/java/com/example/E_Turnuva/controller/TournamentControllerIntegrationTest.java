package com.example.E_Turnuva.controller;

import com.example.E_Turnuva.entity.Tournament;
import com.example.E_Turnuva.service.TournamentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TournamentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // Tarayıcı taklidi yapan araç

    @Autowired
    private ObjectMapper objectMapper; // Java nesnesini JSON'a çeviren araç

    @Test
    public void testCreateAndGetTournament() throws Exception {
        // 1. ADIM: Yeni bir turnuva oluştur (POST isteği at)
        Tournament tournament = new Tournament();
        tournament.setName("Entegrasyon Testi Turnuvası");
        tournament.setPrizePool(5000.0);

        // Nesneyi JSON formatına çevir
        String tournamentJson = objectMapper.writeValueAsString(tournament);

        // POST isteğini gönder ve 200 OK aldığını doğrula
        mockMvc.perform(post("/api/tournaments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tournamentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Entegrasyon Testi Turnuvası"));

        // 2. ADIM: Turnuvaları listele (GET isteği at) ve kaydettiğimizin orada olduğunu gör
        mockMvc.perform(get("/api/tournaments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Entegrasyon Testi Turnuvası"));
    }
}