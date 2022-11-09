package com.example.newgameshop.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.newgameshop.entity.Game;
import com.example.newgameshop.entity.JsonResult;
import com.example.newgameshop.entity.Score;
import com.example.newgameshop.service.GameService;
import com.example.newgameshop.service.ScoreService;
import com.example.newgameshop.untils.UserVerify;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Setter(onMethod_ = {@Autowired})
public class ScoreController {
    private ScoreService scoreService;
    private GameService gameService;

    //游戏评分
    @PostMapping("/addscore/{gameId}/{gameScore}")
    @ResponseBody
    public JsonResult addScore(@PathVariable Integer gameId,@PathVariable Integer gameScore, HttpSession session){
        Game game=gameService.findGame(gameId);
        Integer userId= UserVerify.verify(session);
        Score score = new Score(0, game.getGameId(), gameScore,userId);
        Score scoreUser=scoreService.findScoreOnly(userId,game.getGameId());
        game.setGameStore(gameScore);
        if(ObjectUtil.isEmpty(scoreUser)){
            scoreService.addScore(score);
        }else{
            score.setScoreId(scoreUser.getScoreId());
            scoreService.updateScore(score);
        }
        List<Score> scoreList=scoreService.findScoreGame(game.getGameId());
        Integer sum = scoreList.stream().map((item) -> item.getGameStore()).reduce(0, Integer::sum);
        game.setGameStore(sum/scoreList.size());
        Boolean flag=gameService.updateGame(game);
        if(flag){
            return new JsonResult(450,"评分成功");
        }else {
            return new JsonResult(200,"评分失败");
        }

    }
}
