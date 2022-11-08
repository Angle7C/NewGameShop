package com.example.newgameshop.controller;

import com.example.newgameshop.entity.Game;
import com.example.newgameshop.entity.JsonResult;
import com.example.newgameshop.service.GameService;
import com.example.newgameshop.service.ScoreService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@Setter(onMethod_ = {@Autowired})
public class ScoreController {
    private ScoreService scoreService;
    private GameService gameService;

    //游戏评分
    @RequestMapping("/addscore/{gameId}/{gameScore}")
    @ResponseBody
    public JsonResult addScore(@PathVariable Integer gameId,@PathVariable Integer gameScore, HttpSession session){
        Game game=gameService.findGame(gameId);
        game.setGameStore(gameScore);
        Boolean flag=gameService.updateGame(game);
        if(flag){
            return new JsonResult("评分成功",true);
        }else {
            return new JsonResult("评分失败",false);
        }

    }
}
