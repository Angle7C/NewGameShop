package com.example.newgameshop.service;

import com.gameshop.entity.Score;

import java.util.List;

public interface ScoreService {
    public List<Score> findScoreGame(Integer id);
    public Score findScoreOnly(Integer userId,Integer gameId);
    public void addScore(Score score);
    public void updateScore(Score score);
    public void deleteGameId(Integer gameId);
}
