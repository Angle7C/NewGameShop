package com.example.newgameshop.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.newgameshop.entity.*;
import com.example.newgameshop.service.BuyCarService;
import com.example.newgameshop.service.GameService;
import com.example.newgameshop.service.IndentService;
import com.example.newgameshop.service.UserService;
import com.example.newgameshop.untils.UserVerify;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Setter(onMethod_ = {@Autowired})
public class IndentController {
    private IndentService indentService;
    private GameService gameService;
    private BuyCarService buyCarService;
    private UserService userService;

    //提交订单，完成购买
    @GetMapping("/submitindent/{gameId}")
    public JsonResult addIndent(@PathVariable Integer gameId, HttpSession session){
            Indent indent = new Indent();
            BuyCar buyCar = new BuyCar();
            Float gameValue = gameService.findGame(gameId).getGameValue();
            Integer userId = UserVerify.verify(session);
            indent.setGameId(gameId);
            indent.setValue(gameValue);
            indent.setUserId(userId);
            buyCar.setBuyCarId(buyCarService.findBuyCar(userId).getBuyCarId());
            Boolean flag=buyCarService.deleteBuyCar(buyCar);
            if(flag){
                indentService.addIndent(indent);
                return new JsonResult("完成购买",true);
            }else {
                return new JsonResult("未完成购买",false);
            }


    }

    //申请退款
    @DeleteMapping("/cancelindent/{orderId}")
    public JsonResult cancelIndent(@PathVariable Integer orderId) throws ParseException {
        Indent indent=indentService.findOrder(orderId);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM--dd");
        Map<String,Object> map=new TreeMap<String,Object>();
        Calendar calendar=Calendar.getInstance();
        Date A=sdf.parse(sdf.format(new Date()));
        Date B=sdf.parse(sdf.format(indent.getDate()));
        if((A.getTime()-B.getTime())/(1000*3600*24)>3) {
            return new JsonResult("无法退款，超过3天",false);
        }else {
            indentService.deleteIndent(indent);
            User user=userService.findId(indent.getUserId());
            user.setMoney(user.getMoney()+indent.getValue());
            userService.updateUser(user);
            return new JsonResult("退款成功",true);
        }
    }

    //
    @PostMapping("/indentpage")
    public JsonResult findIndent(HttpSession session){
        Integer userId = UserVerify.verify(session);
        if(ObjectUtil.isEmpty(userId)){
            return new JsonResult(200,"没有登录");
        }
        Map<String,Object> map=new TreeMap<String,Object>();
        List<Indent> L = indentService.findUserId(userId);
        List<Integer> orderIdList=new ArrayList<Integer>();
        List<Game> gameIdList=new ArrayList<Game>();

        for(Indent item:L){
            orderIdList.add(item.getOrderId());
            gameIdList.add(gameService.findGame(item.getGameId()));
        }
        map.put("orderIdList",orderIdList);
        map.put("gameIdList",gameIdList);
        return new JsonResult("查找成功",map);
    }

    //批量购买游戏
    @PostMapping("/addindentlist")
    public JsonResult addIndentList(@ModelAttribute List<Integer> gameIdList,@ModelAttribute List<Float> valueList,HttpSession session){

        try {
            for (Integer gameId : gameIdList) {
                Indent indent=new Indent();
                indent.setUserId(UserVerify.verify(session));
                indent.setGameId(gameId);
                for (Float value : valueList){
                    indent.setValue(value);
                }
                indentService.addIndent(indent);
            }
            return new JsonResult("购买成功",true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("购买失败",false);
        }
    }
}
