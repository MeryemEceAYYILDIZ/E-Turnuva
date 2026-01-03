package com.example.E_Turnuva.service;

import com.example.E_Turnuva.entity.Match;
import com.example.E_Turnuva.entity.Score;
import com.example.E_Turnuva.repository.MatchRepository;
import com.example.E_Turnuva.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    @Autowired private ScoreRepository scoreRepository;
    @Autowired private MatchRepository matchRepository;

    public Score setScore(Long matchId, int t1Score, int t2Score) {
        Match match = matchRepository.findById(matchId).orElseThrow();

        Score score = new Score();
        score.setMatch(match);
        score.setTeam1Score(t1Score);
        score.setTeam2Score(t2Score);

        // Kazananı basitçe belirle
        if(t1Score > t2Score) {
            score.setWinnerTeamId(match.getTeam1().getId());
        } else if (t2Score > t1Score) {
            score.setWinnerTeamId(match.getTeam2().getId());
        }

        return scoreRepository.save(score);
    }
}