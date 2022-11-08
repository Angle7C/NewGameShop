package com.example.newgameshop.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.newgameshop.entity.FileDomain;
import com.example.newgameshop.entity.Picture;
import com.example.newgameshop.exception.MyException;
import com.example.newgameshop.exception.enums.ErrorEnums;
import com.example.newgameshop.service.PictureService;
import com.example.newgameshop.service.UploadService;
import com.example.newgameshop.untils.MinIO;
import io.minio.GetObjectResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Setter(onMethod_ ={@Resource} )
public class UploadServiceImpl implements UploadService {
    private MinIO minIO;

    private PictureService pictureService;
    @Override
    public String multiFileUpload(FileDomain fileDomain,String buckName,Integer gameId) {
        List<MultipartFile> files = fileDomain.getFiles();
        String path=null;
        if (!FileUtil.getSuffix(files.get(0).getOriginalFilename()).equals("rar")) {
            throw new MyException(ErrorEnums.CHECK_ERROR);
        }else{
            String gameName = gameId + "_0.rar";
            path=minIO.uploadFile(files.get(0),"game",gameName);
        }
        minIO.createBucket(buckName);
//        List<Picture> list = new ArrayList<Picture>();
        MultipartFile file = null;
        minIO.createBucket("image");
        log.info("[图片上传中]");
        for (int index = 1; index < files.size(); index++) {
            file = files.get(index);
            String pictureName=gameId+"_"+index+"."+FileUtil.getSuffix(file.getOriginalFilename());
            String paths = minIO.uploadFile(file, "image", pictureName);
            Picture picture = new Picture(gameId, paths,index);
//            list.add(picture);
            pictureService.addPicture(picture);
        }
        log.info("[图片上传成功]");
        return path;
    }

    @Override
    public GetObjectResponse downloadFile(String path, String bucketName) {
       return minIO.downloadFile(bucketName,path);
    }
}
