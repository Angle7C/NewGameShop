package com.example.newgameshop.controller;

import com.gameshop.entity.Game;
import com.gameshop.entity.Score;
import com.gameshop.entity.User;
import com.gameshop.service.GameService;
import com.gameshop.service.ScoreService;
import com.gameshop.until.ObjectAndString;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ScoreController {
    private ScoreService scoreService;
    private GameService gameService;

    public GameService getGameService() {
        return gameService;
    }
    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public ScoreService getScoreService() { return scoreService; }
    @Autowired
    public void setScoreService(ScoreService scoreService) { this.scoreService = scoreService; }
    @RequestMapping("/addscore.html")
    @ResponseBody
    public Map<String,Object> addScore(@RequestBody ObjectAndString<Integer,Integer> t, Model model, HttpSession session){
        Map<String,Object> map=new HashMap<String, Object>();
        Game game=gameService.findGame(t.getFirst());
        game.setGameStore(t.getSecond());
        gameService.updateGame(game);
        map.put("message",true);
        map.put("data","评分成功");
        return map;
    }

}
