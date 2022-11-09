package com.example.newgameshop.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.newgameshop.entity.*;
import com.example.newgameshop.exception.MyException;
import com.example.newgameshop.exception.enums.ErrorEnums;
import com.example.newgameshop.service.BuyCarService;
import com.example.newgameshop.service.GameService;
import com.example.newgameshop.service.IndentService;
import com.example.newgameshop.service.UserService;
import com.example.newgameshop.untils.UserVerify;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@Setter(onMethod_ = {@Autowired})
public class BuyCarController {
    private GameService gameService;
    private UserService userService;
    private IndentService indentService;
    private BuyCarService buyCarService;

    //查询购物车
    @GetMapping("/showbuycar")
    public JsonResult findUserId(HttpSession session){
        Integer id= UserVerify.verify(session);
        List<BuyCar> list=buyCarService.findUserId(id);
        List<Game> L=new ArrayList<>();
        for(BuyCar item:list){
            L.add(gameService.findGame(item.getGameId()));
        }
        Map<String,Object> map=new HashMap<>();
//        map.put("message",true);
        map.put("buyCarList",list);
        map.put("gameList",L);
        return new JsonResult("查找成功",map);
    }

    //加入购物车
    @GetMapping("/addgamenumber/{gameId}")
    @ResponseBody
    public JsonResult addGameNumberInBuyCar(@PathVariable Integer gameId, HttpSession session){
        Integer userId= UserVerify.verify(session);
        User user=new User();
        user.setUserId(userId);
        Game game=gameService.findGame(gameId);
        BuyCar buyCar=new BuyCar();
        buyCar.setGameId(game.getGameId());
        buyCar.setUserId(userId);
        buyCarService.addBuyCar(buyCar);
        return new JsonResult(450,"加入购物车成功");
    }

    //删除购物车单个商品
    @DeleteMapping("/deletegamenumber/{buyCarId}")
    public JsonResult deleteGameNumberInBuyCar(@PathVariable Integer buyCarId){
        BuyCar buyCar =new BuyCar();
        buyCar.setBuyCarId(buyCarId);
        Boolean flag=buyCarService.deleteBuyCar(buyCar);
        if(flag){
            return new JsonResult("删除成功",true);
        }else {
            return new JsonResult("删除失败",false);
        }
    }

    //批量删除购物车商品
    @DeleteMapping("/deletegamenumberlist")
    public JsonResult deleteBuyCarList(@RequestBody List<Integer> buyCarIdList,HttpSession session){
        BuyCar buyCar=new BuyCar();
        Boolean flag=false;
        Integer userId = UserVerify.verify(session);
        if(ObjectUtil.isEmpty(userId)){
            throw  new MyException(ErrorEnums.CHECK_ERROR);
        }
        for (Integer id : buyCarIdList) {
            buyCar.setBuyCarId(id);
            flag=buyCarService.deleteBuyCar(buyCar);
            if(!flag){
                break;
            }
        }
        if(flag){
            return new JsonResult("删除成功",true);
        }else {
            return new JsonResult("删除失败",false);
        }
    }

    //购买游戏
    @PostMapping("/buyGame/{buyCarId}")
    @Transactional
    public JsonResult buyCarToIndent(@PathVariable Integer buyCarId , HttpSession session){
        Map<String,Object> map=new TreeMap<String, Object>();
        Integer userId = UserVerify.verify(session);
        if(ObjectUtil.isEmpty(userId)){
           throw  new MyException(ErrorEnums.CHECK_ERROR);
        }
        BuyCar buyCar=buyCarService.findBuyCar(buyCarId);
        Integer gameId = buyCar.getGameId();

        User user=userService.findId(userId);
        Game game=gameService.findGame(gameId);
        if(user.getMoney()<game.getGameValue()){
            return new JsonResult(450,"没钱拉");
        }else{
            user.setMoney(user.getMoney()-game.getGameValue());
            buyCarService.deleteBuyCar(buyCar);
            indentService.addIndent(new Indent(userId,0,gameId,game.getGameValue(),new Date()));
            userService.updateUser(user);
            return new JsonResult("购买成功",false);
        }

    }

}
