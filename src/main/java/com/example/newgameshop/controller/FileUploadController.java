package com.example.newgameshop.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.newgameshop.entity.FileDomain;
import com.example.newgameshop.entity.Game;
import com.example.newgameshop.entity.JsonResult;
import com.example.newgameshop.entity.Picture;
import com.example.newgameshop.exception.enums.ErrorEnums;
import com.example.newgameshop.service.GameService;
import com.example.newgameshop.service.PictureService;
import com.example.newgameshop.service.UploadService;
import com.example.newgameshop.untils.UserVerify;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@Setter(onMethod_ = {@Resource})
public class FileUploadController {
    private UploadService uploadService;
    private GameService gameService;

    private PictureService pictureService;

    private static final String header="http://124.221.237.233/";
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
                -UserVerify.verify(session),
                description.get(1),
                3,null );
        gameService.addGame(game);
        String path=uploadService.multiFileUpload(fileDomain,"game", game.getGameId());
        game.setPath(path);
        gameService.updateGame(game);
        return new JsonResult<>(450,"上传成功");
    }
    @GetMapping(value="/download/{gameId}")
    public void download(HttpServletResponse response, @PathVariable("gameId") Integer gameId, @RequestHeader("User-Agent") String userAgent) throws IOException {
        Game game = gameService.findGame(gameId);
        InputStream inputStream = uploadService.downloadFile(game.getPath(), "game");
//        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
//        builder.contentLength(inputStream.available());
//        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        String filename = URLEncoder.encode(game.getPath(), "UTF-8");
//        if (userAgent.indexOf("MSIE") > 0) {
//            builder.header("Content-Disposition", "attachment; filename=" + filename);
//        } else {
//            builder.header("Content-Disposition", "attacher; filename*=UTF-8''" + filename);
//        }
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
//        builder.body(readAll(inputStream));
        IoUtil.copy(inputStream,response.getOutputStream());
        return;
    }
    @GetMapping("/findfile/{gameId}")
    public JsonResult<?> showPhoto(Integer gameId,HttpServletResponse response){

        Picture pictures=pictureService.findGameId(gameId);
        JsonResult<String> jsonResult=new JsonResult<>();

        List<String> list=new ArrayList<>();

            list.add(header+pictures.getPath());
        jsonResult.setDatas(list);
        return jsonResult;
    }

    private byte[] readAll(InputStream inputStream) throws IOException {
        int available = inputStream.available();
        byte[] buff=new byte[available];
        inputStream.read(buff);
        return buff;
    }

}
