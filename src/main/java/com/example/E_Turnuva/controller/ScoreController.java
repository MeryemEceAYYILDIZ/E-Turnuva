package com.example.E_Turnuva.controller;

import com.example.E_Turnuva.entity.Score;
import com.example.E_Turnuva.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    @Autowired private ScoreService scoreService;

    @PostMapping
    public Score setScore(@RequestParam Long matchId,
                          @RequestParam int score1,
                          @RequestParam int score2) {
        return scoreService.setScore(matchId, score1, score2);
    }
}