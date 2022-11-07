package com.example.newgameshop.mapper;

import com.gameshop.entity.Game;
import com.gameshop.entity.Score;

public interface ScoreMapper {
    public Score findScore(Integer id);
    public void addScore(Game game, Score score);
    public void updateScore(Score score);
}
