package com.example.newgameshop.controller;

import com.example.newgameshop.entity.Game;
import com.example.newgameshop.entity.JsonResult;
import com.example.newgameshop.entity.Page;
import com.example.newgameshop.entity.Picture;
import com.example.newgameshop.service.BuyCarService;
import com.example.newgameshop.service.GameService;
import com.example.newgameshop.service.PictureService;
import com.example.newgameshop.untils.UserVerify;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.*;

@RestController
@Setter(onMethod_ = {@Autowired})
public class GoodsController {
    private GameService gameService;
    private PictureService pictureService;

    //根据gameId查找游戏
    @GetMapping("/findgame/{gameId}")
    public JsonResult findGame(@PathVariable Integer gameId, HttpSession session){

        Integer userId= UserVerify.verify(session);
        if(userId<0||userId==0){
          return new JsonResult("message",false);
        }else{
          return new JsonResult("message",gameService.findGame(gameId));
        }
    }

    //新增游戏
    @PostMapping("/addgame")
    public JsonResult addGame(@ModelAttribute Game game){
        Boolean flag=gameService.addGame(game);
        if(flag){
            return new JsonResult("message",true);
        }else {
            return new JsonResult("message",false);
        }
    }

    //更新游戏信息
    @PutMapping("/updategame")
    public JsonResult updateGame(@ModelAttribute Game game){
        Boolean flag=gameService.updateGame(game);
        if(flag){
            return new JsonResult("message",true);
        }else {
            return new JsonResult("message",false);
        }
    }

    //删除游戏
    @DeleteMapping("/deletegame/{gameId}")
    public JsonResult deleteGame(@PathVariable Integer gameId){
        Game game=gameService.findGame(gameId);
        Boolean flag=gameService.deleteGame(game);
        if(flag){
            return new JsonResult("message",true);
        }else {
            return new JsonResult("message",false);
        }
    }

    //限制页数显示
    @GetMapping("/gamepagesize/{size}")
    public JsonResult pageSize(@PathVariable Integer size){
        Map<String,Object> map=new TreeMap<String,Object>();
        Page page=new Page();
        page.setPageSize(size);
        List<Game> L = gameService.findAllZero();
        page.setTotalPages( L.size() / page.getPageSize()+(L.size()%page.getPageSize()==0 ? 0 : 1 ));
        page.setCurrentPage(1);
        map.put("message",true);
        map.put("data",page);
        return new JsonResult(450,"查找成功",map);
    }

    //游戏类型，名字，页数分页显示
    @PostMapping("/gamepage")
    public JsonResult gamePage(@RequestBody Map<String,Object> t){
        Map<String,Object> map=new TreeMap<String,Object>();
        Object bool=t.get("type");
        Object sizes= t.get("size");
        Integer size=(Integer) t.get("size");
        Object name=t.get("Name");
        List<Game> gameTypePage = gameService.findPageSize( (String) bool,(String) name);
        map.put("message",true);
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(size);
        page.setTotalPages(gameTypePage.size()%size==0?gameTypePage.size()/size:gameTypePage.size()/size+1);
        System.out.println(gameTypePage.size());
        map.put("data",page);
        return new JsonResult(450,"查找成功",map);
    }

    //根据游戏类型分页查找游戏
    @PostMapping ("/gametypepage")
    @ResponseBody
    public JsonResult gameTypePage(@RequestBody HashMap<String, Object> t){
        Map<String,Object> map=new TreeMap<>();
        Map<String,Object> m=new TreeMap<>();
        String bool=(String)t.get("type");
        String name=(String)t.get("Name");
        Integer current= (Integer )t.get("currentPage");
        Integer size= (Integer )t.get("pageSize");
        System.out.println(t);
        Integer total= (Integer )t.get("totalPages");
        Page page=new Page();
        page.setCurrentPage(current);
        page.setPageSize(size);
        page.setTotalPages(total);
        System.out.println(page.getPageSize());
        List<Game> gameTypePage = gameService.findGameTypePage(page.getPageSize(), page.getCurrentPage(), bool==""?null:bool,name==""?null:name);
        List<Picture> pictures=new ArrayList<>();
        for(Game game:gameTypePage){
            pictures.add(pictureService.findGameId(game.getGameId()));
        }
//        pictureService
        map.put("message",true);
        m.put("array",gameTypePage);
        m.put("pictures",pictures);
        m.put("page",page);
        map.put("data",m);
        return new JsonResult(450,"查找成功",map);
    }

    //取消游戏上架
    @PutMapping("/cancelgameup/{gameId}/{flag}")
    public JsonResult cancelGameUp(@PathVariable Integer gameId,@PathVariable Boolean flag,HttpSession session){
        Map<String ,Object> map=new TreeMap<>();
        Integer userId=UserVerify.verify(session);
        Game game=gameService.findGame(gameId);
        if(game.getUserId()<0&&flag==false){
            map.put("message",true);
            map.put("data","停止申请");
        }else if(game.getUserId()<0&& flag==true){
            game.setUserId(-game.getUserId());
            gameService.updateGame(game);
            map.put("message",true);
            map.put("data","通过申请");
        }else{
            map.put("message",false);
            map.put("data","游戏已上架");
        }
        return new JsonResult("判断完成",map);
    }
}
