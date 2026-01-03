package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Game;
import com.example.E_Turnuva.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameService {
    @Autowired private GameRepository gameRepository;

    public List<Game> getAllGames() { return gameRepository.findAll(); }

    public void saveGame(Game game) { gameRepository.save(game); }

    public void deleteGame(Long id) { gameRepository.deleteById(id); }
}