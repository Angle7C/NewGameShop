package com.example.newgameshop.controller;

import com.gameshop.entity.Game;
import com.gameshop.entity.Page;
import com.gameshop.service.GameService;
import com.gameshop.service.PictureService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class GameController {
    private PictureService pictureService;

    public PictureService getPictureService() {
        return pictureService;
    }
    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    private GameService gameService;
    public GameService getGameService() { return gameService; }
    @Autowired
    public void setGameService(GameService gameService){ this.gameService=gameService; }
    @RequestMapping("/findgame.html")
    @ResponseBody
    public Map<String,Object> findGame( Integer gameId,Model model, HttpSession session){
        Game game=new Game();
        game=gameService.findGame(gameId);
        Map<String,Object> map=new HashMap<>();
        Integer userId=UserVerify.verify(session);
        if(userId<0||userId==0){
            map.put("message",false);
        }else{
            map.put("message",game);
        }
        map.put("data",null);
        return map;
    }

    @RequestMapping("/addgame.html")
    @ResponseBody
    @Transactional
    public Map<String,Object> addGame(@RequestBody Game game,Model model, HttpSession session){
        gameService.addGame(game);
        Map<String,Object> map=new HashMap<>();
        map.put("message",true);
        map.put("data",null);
        return map;
    }
    @RequestMapping("/updategame.html")
    @ResponseBody
    @Transactional
    public Map<String,Object> updateGame(@RequestBody Game game,Model model, HttpSession session){
        gameService.updateGame(game);
        Map<String,Object> map=new HashMap<>();
        map.put("message",true);
        map.put("data",null);
        return map;
    }
    @RequestMapping("/deletegame.html")
    @ResponseBody
    @Transactional
    public Map<String,Object> deleteGame(@RequestParam("gameId") Integer gameId, Model model, HttpSession session){
        Game game=gameService.findGame(gameId);
        gameService.deleteGame(game);
        Map<String,Object> map=new HashMap<>();
        map.put("message",true);
        map.put("data","删除成功");
        return map;
    }
    @RequestMapping("/gamepagesize.html")
    @ResponseBody
    public Map<String,Object> pageSize(Integer size, HttpSession session , Model model){
        Map<String,Object> map=new TreeMap<String,Object>();
            Page page=new Page();
            page.setPageSize(size);
            List<Game> L = gameService.findAll();
            page.setTotalPages( L.size() / page.getPageSize()+(L.size()%page.getPageSize()==0 ? 0 : 1 ));
            page.setCurrentPage(1);
            map.put("message",true);
            map.put("data",page);
        return  map;
    }
    @RequestMapping("/agamepagesize.html")
    @ResponseBody
    public Map<String,Object> pageAdminSize(@RequestBody Page page ,HttpSession session ,Model model){
        Map<String,Object> map=new TreeMap<String,Object>();
        Map<String,Object> m=new TreeMap<String,Object>();
        List<Game> L = gameService.findGameTypePage(page.getPageSize(),page.getCurrentPage(),null,null);
//        page.setTotalPages(  L.size() / page.getPageSize()+(L.size()%page.getPageSize()==0 ? 0 : 1 ) );
        m.put("array",L);
        m.put("page",page);
        map.put("message",true);
        map.put("data",m);
        return  map;
    }


    @RequestMapping("/gamepage.html")
    @ResponseBody
    public Map<String,Object> gamePage(@RequestBody Map<String,Object> t, Model model, HttpSession session){
        Map<String,Object> map=new TreeMap<String,Object>();
        Object bool=t.get("type");
        Object sizes= t.get("size");
        Integer size=Integer.valueOf((String)sizes);
        Object name=t.get("Name");
        List<Game> gameTypePage = gameService.findPageSize( (String) bool,(String) name);
        map.put("message",true);
        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(size);

        page.setTotalPages(gameTypePage.size()%size==0?gameTypePage.size()/size:gameTypePage.size()/size+1);
        System.out.println(gameTypePage.size());
        map.put("data",page);
        return map;
    }

    @RequestMapping("/gametypepage.html")
    @ResponseBody
    public Map<String,Object> gameTypePage(@RequestBody HashMap<String, Object> t, Model model, HttpSession session){
        Map<String,Object> map=new TreeMap<>();
        Map<String,Object> m=new TreeMap<>();
        String bool=t.get("type")+"";
        String name=t.get("Name")+"";
        Integer current= (Integer )t.get("currentPage");
        Integer size= (Integer )t.get("pageSize");
        Integer total= (Integer )t.get("totalPages");
        Page page=new Page();
        page.setCurrentPage(current);
        page.setPageSize(size);
        page.setTotalPages(total);
        if(bool.equals("null")) bool="";
        if(name.equals("null")) name="";
        List<Game> gameTypePage = gameService.findGameTypePage(page.getPageSize(), page.getCurrentPage(), "".equals(bool)?null:bool,"".equals(name)?null:name);
        map.put("message",true);
        m.put("array",gameTypePage);
        m.put("page",page);
        map.put("data",m);
        return map;
    }
    @RequestMapping("cancelgameup.html")
    @ResponseBody
    @Transactional
    public Map<String,Object> cancelGameUp(@RequestBody ObjectAndString<Integer,Boolean> t,Model model,HttpSession session ){
        Map<String ,Object> map=new TreeMap<>();
        Integer userId=UserVerify.verify(session);
        Game game=gameService.findGame(t.getFirst());
        if(game.getUserId()<0&&t.getSecond()==false){
            map.put("message",true);
            map.put("data","停止申请");
            gameService.deleteGame(game);
            pictureService.deletePicture(game.getGameId());
        }else if(game.getUserId()<0&& t.getSecond()==true){
            game.setUserId(-game.getUserId());
            gameService.updateGame(game);
            map.put("message",true);
            map.put("data","通过申请");
        }else{
            map.put("message",false);
            map.put("data","游戏已上架");
        }
        return map;
    }
    @RequestMapping("/zerogamepagesize.html")
    @ResponseBody
    public Map<String,Object>  zeroGamePageSize(@RequestParam("size") Integer size,HttpSession session){
        Map<String,Object> map=new TreeMap<>();
        List<Game> allZero = gameService.findAllZero();
        Page page=new Page();
        page.setPageSize(size);
        page.setCurrentPage(1);
        page.setTotalPages((allZero.size()%size==0? allZero.size() /size: allZero.size()/size+1));
        map.put("message",true);
        map.put("data",page);
        return map;
    }
    @RequestMapping("/zerogamepage.html")
    @ResponseBody
    public Map<String,Object>  zeroGamePage(@RequestBody Page page,HttpSession session){
        Map<String,Object> map=new TreeMap<>();
        Map<String,Object> m=new TreeMap<>();
        List<Game> allZero = gameService.findGameTypePageZero(page.getPageSize(),page.getCurrentPage(),null);
        map.put("message",true);
        m.put("array",allZero);
        m.put("page",page);
        map.put("data",m);
        return map;
    }
}
