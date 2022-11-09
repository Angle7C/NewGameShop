package com.example.newgameshop.controller;

import com.example.newgameshop.entity.*;
import com.example.newgameshop.service.BuyCarService;
import com.example.newgameshop.service.GameService;
import com.example.newgameshop.service.PictureService;
import com.example.newgameshop.untils.UserVerify;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        Game game=gameService.findGame(gameId);
        Picture picture=pictureService.findGameId(gameId);
        Integer userId=UserVerify.verify(session);
        if(userId<0||userId==0){
          return new JsonResult(200,"message",false);
        }else{
          return new JsonResult(450,"message",new ObjectAndString<Game,Picture>(game,picture));
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
    @GetMapping("/gamepage/{pageNum}/{pageSize}")
    @ResponseBody
    public JsonResult gamePage(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Game> gameList=gameService.findAll();
        List<Picture> pictures=new ArrayList<>();
        for(Game game:gameList){
            pictures.add(pictureService.findGameId(game.getGameId()));
        }
        PageInfo<Game> gamePageInfo=new PageInfo<>(gameList);
        ObjectAndString<Game,Picture> gamePageInfoAndPicture=new ObjectAndString(gamePageInfo,pictures);
        return new JsonResult(450,"查找成功",gamePageInfoAndPicture);
    }

    //根据游戏类型分页查找游戏
    @GetMapping ("/gametypepages/{type}/{pageNum}/{pageSize}")
    @ResponseBody
    public JsonResult gameTypePage(@PathVariable String type,@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Game> gameList=gameService.findGameByType(type);
        List<Picture> pictures=new ArrayList<>();
        for(Game game:gameList){
            pictures.add(pictureService.findGameId(game.getGameId()));
        }
        PageInfo<Game> gamePageInfo=new PageInfo<>(gameList);
        ObjectAndString<Game,Picture> gamePageInfoAndPicture=new ObjectAndString(gamePageInfo,pictures);
        System.out.println(gamePageInfoAndPicture);
        return new JsonResult(450,"查找成功",gamePageInfoAndPicture);
    }
    //根据搜索查找游戏
    @GetMapping ("/gamewordpages/{word}/{pageNum}/{pageSize}")
    @ResponseBody
    public JsonResult gameWordPage(@PathVariable String word,@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Game> gameList=gameService.findGameByWord(word);
        List<Picture> pictures=new ArrayList<>();
        for(Game game:gameList){
            pictures.add(pictureService.findGameId(game.getGameId()));
        }
        PageInfo<Game> gamePageInfo=new PageInfo<>(gameList);
        ObjectAndString<Game,Picture> gamePageInfoAndPicture=new ObjectAndString(gamePageInfo,pictures);
        System.out.println(gamePageInfoAndPicture);
        return new JsonResult(450,"查找成功",gamePageInfoAndPicture);
    }

    //审核游戏
    @PostMapping("audit")
    public JsonResult cancelGameUp(@RequestBody Map<String,Object> map) {
        String gameid=(String) map.get("first");
        Integer gameId=Integer.parseInt(gameid);
        Boolean flag=(Boolean) map.get("second");
        Game game=gameService.findGame(gameId);
        if(game.getUserId()<0&&flag==false){
            gameService.deleteGame(game);
            return new JsonResult(450,"拒绝通过成功!");
        }else if(game.getUserId()<0&& flag==true){
            game.setUserId(-game.getUserId());
            gameService.updateGame(game);
            return new JsonResult(450,"申请通过成功!");
        }else{
            return new JsonResult(450,"该游戏已上架!");
        }
    }


    /*
        后台游戏控制层
    */
    //所有游戏
    @GetMapping("games/{pageNum}/{pageSize}")
    public JsonResult ShowPublishedGames(@PathVariable int pageNum,@PathVariable int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Game> games=gameService.findAll();
        PageInfo<Game> pageInfo=new PageInfo<>(games);
        return new JsonResult(450,"SUCCESS",pageInfo);
    }

    //删除游戏
    @DeleteMapping("games")
    public JsonResult DeleteGame(@RequestBody Game game){
        Game g=gameService.findGame(game.getGameId());
        gameService.deleteGame(g);
        return new JsonResult(450,"删除成功");
    }

    //修改游戏信息
    @PutMapping("games")
    public JsonResult UpdateGame(@RequestBody Game game){
        Game g=gameService.findGame(game.getGameId());
        g.setGameName(game.getGameName());
        g.setGameValue(game.getGameValue());
        g.setGameStore(game.getGameStore());
        gameService.updateGame(g);
        System.out.println(g.getGameStore());
        return new JsonResult(450,"SUCCESS");
    }

    //待审核游戏
    @GetMapping("nopublishgames/{pageNum}/{pageSize}")
    public JsonResult ShowNoPublishGames(@PathVariable int pageNum,@PathVariable int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Game> games=gameService.findAllZero();
        PageInfo<Game> pageInfo=new PageInfo<>(games);
        return new JsonResult(450,"SUCCESS",pageInfo);
    }

}
