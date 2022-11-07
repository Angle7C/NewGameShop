package com.example.newgameshop.controller;

import com.gameshop.entity.Picture;
import com.gameshop.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PictureController {
    private PictureService pictureService;
    public PictureService getPictureService() {
        return pictureService;
    }
    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @RequestMapping("addpicture.html")
    @ResponseBody
    public Map<String,Object> addPicture(@RequestBody Integer gameId,String path, Model model, HttpSession session){
        Picture picture=new Picture();
        picture.setGameId(gameId);
        picture.setPath(path);
        pictureService.addPicture(picture);
        Map<String,Object> map=new HashMap<>();
        map.put("message",true);
        map.put("data",null);
        return map;
    }
    @RequestMapping("deletepicture.html")
    @ResponseBody
    public Map<String,Object> deletePicture(@RequestBody Integer pictureId, Model model, HttpSession session){
        Picture picture=new Picture();
        picture.setPictureId(pictureId);
        pictureService.deletePicture(picture);
        Map<String,Object> map=new HashMap<>();
        map.put("message",true);
        map.put("data",null);
        return map;
    }
    @RequestMapping("updatepicture.html")
    @ResponseBody
    public Map<String,Object> updatePicture(@RequestBody Integer pictureId,Integer gameId, Model model, HttpSession session){
        Picture picture=new Picture();
        picture.setPictureId(pictureId);
        picture.setGameId(gameId);
        pictureService.updatePicture(picture);
        Map<String,Object> map=new HashMap<>();
        map.put("message",true);
        map.put("data",null);
        return map;
    }

}
