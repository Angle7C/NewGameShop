package com.example.newgameshop.controller;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gameshop.entity.Game;
import com.gameshop.entity.Picture;
import com.gameshop.service.GameService;
import com.gameshop.service.PictureService;
import com.gameshop.until.Files;
import com.gameshop.until.ObjectAndString;
import com.gameshop.until.UserVerify;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.gameshop.entity.FileDomain;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class FileUploadController {
    private GameService gameService;
    private PictureService pictureService;

    public PictureService getPictureService() {
        return pictureService;
    }
    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    public  GameService getGameService() {
        return gameService;
    }
    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping("/upgame.html")
    @ResponseBody
    @Transactional
    public Map<String,Object> multiFileUpload(@ModelAttribute FileDomain fileDomain, HttpServletRequest request, HttpSession session){
        List<String> description = fileDomain.getDescription();//名称，类型，价格，message
        Game game=new Game();
        game.setGameName(description.get(0));
        game.setGameType(description.get(1));
        game.setGameValue(Float.valueOf(description.get(2)));
        game.setGameStore(3);
        game.setUserId(-UserVerify.verify(session));
        game.setMessage(description.get(3));
        gameService.addGame(game);
        Integer gameId=game.getGameId();
        String realpath = request.getSession().getServletContext().getRealPath("\\Game\\"+gameId);
        System.out.println( realpath );
        File targetDir = new File(realpath);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        List<MultipartFile> files = fileDomain.getMyfile();

        Map<String,Object> map=new TreeMap<>();
            String exe = files.get(0).getOriginalFilename();
            if(Files.getsuff(exe).equals(".rar")){
                File targetFile = new File(realpath,"0.rar");
                try {
                    files.get(0).transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                game.setPath(realpath+"\\0.rar");
                gameService.updateGame(game);
            }else{
                map.put("message",false);
                map.put("data","上传rar错误");
                return  map;
            }
        for (int i = 1; i < files.size(); i++){
            MultipartFile file = files.get(i);
            String fileName = file.getOriginalFilename();
            fileName=String.format(Integer.valueOf(i).toString(), "%04d")+Files.getsuff(fileName );
            File targetFile = new File(realpath, fileName);
            // 上传
            try {
                file.transferTo(targetFile);
                Picture picture=new Picture();
                picture.setGameId(game.getGameId());
                picture.setPath(realpath+"\\"+fileName);
                pictureService.addPicture(picture);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("message",false);
                map.put("data","上传错误");
                return  map;
                }
            }
        map.put("message",true);
        map.put("data","上传成功");
        return  map;
    }
    @RequestMapping(value="/download")
     public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("gameId") Integer gameId, @RequestHeader("User-Agent") String userAgent) throws IOException {
                 // 下载文件的路径
                String path = gameService.findGame(gameId).getPath();
                 // 构建File
                 File file = new File(path);
                 // ok表示http请求中状态码200
                 ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
                 // 内容长度
                 builder.contentLength(file.length());
                 // application/octet-stream 二进制数据流（最常见的文件下载）
                 builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
                 // 使用URLEncoding.decode对文件名进行解码
                 String filename = URLEncoder.encode(file.getName(), "UTF-8");
                 // 根据浏览器类型，决定处理方式
                if (userAgent.indexOf("MSIE") > 0) {
                     builder.header("Content-Disposition", "attachment; filename=" + filename);
                 } else {
                     builder.header("Content-Disposition", "attacher; filename*=UTF-8''" + filename);
                 }
                 return builder.body(FileUtils.readFileToByteArray(file));
             }

    @RequestMapping("/findfile.html")
    public void showPhoto(Integer gameId, HttpSession session,HttpServletResponse response) {
        try {
            List<Picture> Ls = pictureService.findGameId(gameId);
            String path = Ls.get(0).getPath();
            byte[] fileContent = FileUtils.readFileToByteArray(new File(path));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            // 以byte流的方式打开文件 d:\1.gif
            FileInputStream hFile;
            hFile = new FileInputStream(path);
            //得到文件大小
            int i=hFile.available();
            byte data[]=new byte[i];
            //读数据
            hFile.read(data);
            response.setHeader("Content-Type","image/"+path.substring(path.indexOf(".")+1));
            //得到向客户端输出二进制数据的对象
            OutputStream toClient=response.getOutputStream();
            //输出数据
            toClient.write(data);
            toClient.flush();
            toClient.close();
            hFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getBase64() {
        String imgStr = "";
        try {

            File file = new File("C:\\EThinkTankFile\\20180402160120431.jpg");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length && (numRead = fis.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset != buffer.length) {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }
            fis.close();
            BASE64Encoder encoder = new BASE64Encoder();
            imgStr = encoder.encode(buffer);
             } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
