package com.example.newgameshop.controller;

import com.gameshop.entity.BuyCar;
import com.gameshop.entity.Game;
import com.gameshop.entity.Indent;
import com.gameshop.entity.User;
import com.gameshop.service.BuyCarService;
import com.gameshop.service.GameService;
import com.gameshop.service.IndentService;
import com.gameshop.service.UserService;
import com.gameshop.until.Json;
import com.gameshop.until.ObjectAndString;
import com.gameshop.until.UserVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class BuyCarController {
    private GameService gameService;
    private UserService userService;
    private IndentService indentService;

    public IndentService getIndentService() {
        return indentService;
    }

    @Autowired
    public void setIndentService(IndentService indentService) {
        this.indentService = indentService;
    }

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public GameService getGameService() {
        return gameService;
    }

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    private BuyCarService buyCarService;

    public BuyCarService getBuyCarService() {
        return buyCarService;
    }

    @Autowired
    public void setBuyCarService(BuyCarService buyCarService) {
        this.buyCarService = buyCarService;
    }

    @RequestMapping("/showbuycar.html")
    @ResponseBody
    public Map<String, Object> findUserId(Model model, HttpSession session) {
        Integer id = UserVerify.verify(session);
        List<BuyCar> list = buyCarService.findUserId(id);
        List<Game> L = new ArrayList<Game>();
        for (BuyCar item : list) {
            L.add(gameService.findGame(item.getGameId()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", true);
        map.put("data", new ObjectAndString<List<BuyCar>, List<Game>>(list, L));
        return map;
    }

    @RequestMapping("/addgamenumber.html")
    @ResponseBody
    @Transactional
    public Map<String, Object> addGameNumberInBuyCar(Integer gameId, Model model, HttpSession session) {
        Integer userId = UserVerify.verify(session);
        User user = new User();
        Game game = new Game();
        user.setUserId(userId);
        game = gameService.findGame(gameId);
        Map<String, Object> map = new HashMap<>();
        BuyCar buyCar = new BuyCar();
        buyCar.setGameId(game.getGameId());
        buyCar.setUserId(userId);
        if (game == null) {
            map.put("message", false);
            map.put("data", "没有游戏");
        }
        buyCarService.addBuyCar(buyCar);
        map.put("message", true);
        map.put("data", null);
        return map;
    }

    @RequestMapping("/deletegamenumber.html")
    @ResponseBody
    @Transactional
    public Map<String, Object> deleteGameNumberInBuyCar(@RequestBody Integer buyCarId, Model model, HttpSession session) {
        BuyCar buyCar = new BuyCar();
        buyCar.setBuyCarId(buyCarId);
        buyCarService.deleteBuyCar(buyCar);
        Map<String, Object> map = new HashMap<>();
        map.put("message", true);
        map.put("data", "删除成功");
        return map;
    }

    @RequestMapping("/deletegamenumberlist.html")
    @ResponseBody
    @Transactional
    public Map<String, Object> deleteBuyCarList(@RequestBody Map<String, Object> t, Model model, HttpSession session) {
        Map<String, Object> map = new TreeMap<String, Object>();
        List<Integer> L = (List<Integer>) t.get("list");
        BuyCar buyCar = new BuyCar();
        try {
            for (Integer id : L) {
                buyCar.setBuyCarId(id);
                buyCarService.deleteBuyCar(buyCar);
            }
            map.put("message", true);
            map.put("data", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", false);
            map.put("data", "删除失败");
        }
        return map;
    }

    @RequestMapping("/buyGame.html")
    @ResponseBody
    @Transactional
    public Map<String, Object> buyCarToIndent(@RequestParam Integer buyCarId, Model model, HttpSession session) {
        Map<String, Object> map = new TreeMap<String, Object>();
        BuyCar buyCar = buyCarService.findBuyCar(buyCarId);
        Integer gameId = buyCar.getGameId();
        Integer userId = UserVerify.verify(session);
        User user = userService.findId(userId);
        Game game = gameService.findGame(gameId);
        if (user.getMoney() < game.getGameValue()) {
            map.put("message", false);
            map.put("data", "余额不足");
        } else {
            user.setMoney(user.getMoney() - game.getGameValue());
            buyCarService.deleteBuyCar(buyCar);
            indentService.addIndent(new Indent(userId, 0, gameId, game.getGameValue(), new Date()));
            userService.updateUser(user);
            map.put("message", true);
            map.put("data", "成功");
        }
        return map;

    }
}
