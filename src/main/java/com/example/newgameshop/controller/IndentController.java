package com.example.newgameshop.controller;

import com.gameshop.entity.*;
import com.gameshop.service.BuyCarService;
import com.gameshop.service.GameService;
import com.gameshop.service.IndentService;
import com.gameshop.service.UserService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class IndentController {
    private IndentService indentService;
    private  GameService gameService;
    private BuyCarService buyCarService;
    private UserService userService;

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

    public BuyCarService getBuyCarService() {
        return buyCarService;
    }
    @Autowired
    public void setBuyCarService(BuyCarService buyCarService) {
        this.buyCarService = buyCarService;
    }

    public IndentService getIndentService() { return indentService; }
    @Autowired
    public void setIndentService(IndentService indentService) { this.indentService = indentService; }
    @RequestMapping("/submitindent.html")
    @ResponseBody
    public Map<String,Object> addIndent(@RequestBody Integer gameId, Model model, HttpSession session){
        Map<String, Object> map = new HashMap<>();
        try {
            Indent indent = new Indent();
            BuyCar buyCar = new BuyCar();
            Float gameValue = gameService.findGame(gameId).getGameValue();
            Integer userId = UserVerify.verify(session);
            indent.setGameId(gameId);
            indent.setValue(gameValue);
            indent.setUserId(userId);
            buyCar.setBuyCarId(buyCarService.findBuyCar(userId).getBuyCarId());
            buyCarService.deleteBuyCar(buyCar);
            indentService.addIndent(indent);

            map.put("message", true);
            map.put("data", indent);
            return map;
        }catch (Exception e){
            map.put("message", false);
            map.put("data", "失败");
        }
        return map;
    }
    @RequestMapping("/cancelindent.html")
    @ResponseBody
    public Map<String,Object> cancelIndent(@RequestParam("orderId") Integer orderId,Model model, HttpSession session) throws ParseException {
        Indent indent=indentService.findOrder(orderId);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM--dd");
        Map<String,Object> map=new TreeMap<String,Object>();
        Calendar calendar=Calendar.getInstance();
        Date A=sdf.parse(sdf.format(new Date()));
        Date B=sdf.parse(sdf.format(indent.getDate()));
        if((A.getTime()-B.getTime())/(1000*3600*24)>3) {
            map.put("message", false);
            map.put("data","超过3天");
        }else {
            indentService.deleteIndent(indent);
            User user=userService.findId(indent.getUserId());
            user.setMoney(user.getMoney()+indent.getValue());
            userService.updateUser(user);
            map.put("message", true);
            map.put("data","退款成功");
        }
        return map;
    }
    @RequestMapping("/indentpage.html")
    @ResponseBody
    public Map<String,Object> findIndent(HttpSession session){
        Integer userId = UserVerify.verify(session);
        Map<String,Object> map=new TreeMap<String,Object>();
        List<Indent> L = indentService.findUserId(userId);
        List<ObjectAndString<Integer,Game>> list=new ArrayList<ObjectAndString<Integer,Game>>();
        for(Indent item:L){
            list.add(new ObjectAndString<Integer, Game>(item.getOrderId(),gameService.findGame(item.getGameId())));
        }
        map.put("message",true);
        map.put("data",list);
        return map;
    }
    @RequestMapping("addindentlist.html")
    @ResponseBody
    @Transactional
    public Map<String,Object> addIndentList(@RequestBody List<ObjectAndString<Integer,Float>> gameIdAndValueList,Model model,HttpSession session){
        Map<String,Object> map= new TreeMap<String,Object>();
        Indent indent=new Indent();
        indent.setUserId(UserVerify.verify(session));
        try {
            for (ObjectAndString<Integer,Float> item : gameIdAndValueList) {
                indent.setGameId(item.getFirst());
                indent.setValue(item.getSecond());
                indentService.addIndent(indent);
            }
            map.put("message",true);
            map.put("data","删除成功");
        }catch (Exception e){
            e.printStackTrace();
            map.put("message",false);
            map.put("data","删除失败");
        }
        return map;
    }
}
