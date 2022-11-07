package com.example.newgameshop.mapper;



import com.example.newgameshop.entity.Score;

import java.util.List;

public interface ScoreMapper {
    public List<Score> findScoreGame(Integer gameId);
    public Score findScoreOnly(Integer userId,Integer gameId);
    public void addScore(Score score);
    public void updateScore(Score score);
    public void deleteScoreGame(Integer gameId);

}
