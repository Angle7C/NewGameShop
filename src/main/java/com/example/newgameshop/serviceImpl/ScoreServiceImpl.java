package com.example.newgameshop.serviceImpl;

import com.gameshop.entity.Game;
import com.gameshop.entity.Score;
import com.gameshop.mapper.ScoreMapper;
import com.gameshop.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScoreServiceImpl implements ScoreService {
    private ScoreMapper scoreMapper;
    public ScoreMapper getScoreMapper() { return scoreMapper; }
    @Autowired
    public void setScoreMapper(ScoreMapper scoreMapper) { this.scoreMapper = scoreMapper; }
    public Score findScore(Integer id) { return scoreMapper.findScore(id); }
    public void addScore(Game game, Score score) { scoreMapper.addScore(game,score); }
    public void updateScore(Score score) { scoreMapper.updateScore(score); }
}
