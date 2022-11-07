package com.example.newgameshop.service;



import com.example.newgameshop.entity.Score;

import java.util.List;

public interface ScoreService {
    default List<Score> findScoreGame(Integer id){return null;}
    default Score findScoreOnly(Integer userId,Integer gameId){return null;}
    default Boolean addScore(Score score){return false;}
    default Boolean updateScore(Score score){return false;}
    default Boolean deleteGameId(Integer gameId){return false;}
}
