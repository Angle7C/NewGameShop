package com.example.newgameshop.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.newgameshop.entity.FileDomain;
import com.example.newgameshop.entity.Game;
import com.example.newgameshop.entity.JsonResult;
import com.example.newgameshop.exception.enums.ErrorEnums;
import com.example.newgameshop.service.GameService;
import com.example.newgameshop.service.PictureService;
import com.example.newgameshop.service.UploadService;
import com.example.newgameshop.untils.UserVerify;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Setter(onMethod_ = {@Resource})
public class FileUploadController {
    private UploadService uploadService;
    private GameService gameService;
    @PostMapping("/upgame")
    @Transactional
    public JsonResult<?> upload(FileDomain fileDomain, HttpSession session){
        List<String> description = fileDomain.getDescription();
        if(ObjectUtil.isEmpty(UserVerify.verify(session))){
            JsonResult.fail(ErrorEnums.LOGIN_ERROR.getCode(),"没有登录");
        }

        Game game=new Game(null,
                description.get(0),
                Float.valueOf(description.get(2)),
                description.get(3),
                UserVerify.verify(session),
                description.get(1),
                3,null );
        String path=uploadService.multiFileUpload(fileDomain,"Game");
        game.setPath(path);
        gameService.addGame(game);
        return null;
    }
}
