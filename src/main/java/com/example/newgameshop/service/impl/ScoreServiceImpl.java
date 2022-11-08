package com.example.newgameshop.service.impl;

import com.example.newgameshop.entity.Score;
import com.example.newgameshop.mapper.ScoreMapper;
import com.example.newgameshop.service.ScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Resource
    private ScoreMapper scoreMapper;

    public List<Score> findScoreGame(Integer id){
        List<Score> scoreList=scoreMapper.findScoreGame(id);
        return scoreList;}

    public Score findScoreOnly(Integer userId,Integer gameId){
        Score score=scoreMapper.findScoreOnly(userId,gameId);
        return score;}

    public Boolean addScore(Score score){
        scoreMapper.addScore(score);
        return true;}

    public Boolean updateScore(Score score){
        scoreMapper.updateScore(score);
        return true;}

    public Boolean deleteGameId(Integer gameId){
        scoreMapper.deleteScoreGame(gameId);
        return true;}
}
