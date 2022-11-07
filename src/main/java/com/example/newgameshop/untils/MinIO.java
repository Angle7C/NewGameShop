package com.example.newgameshop.untils;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.newgameshop.exception.MyException;
import com.example.newgameshop.exception.enums.ErrorEnums;
import io.minio.*;
import io.minio.errors.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Setter(onMethod_ = {@Resource})
@Component
@Slf4j
public class MinIO {

    private MinioClient client;

    public void createBucket(String bucketName) {
        BucketExistsArgs args=BucketExistsArgs.builder().bucket(bucketName).build();
        try{
           if(! client.bucketExists(args)){
               MakeBucketArgs bucketArgs=MakeBucketArgs.builder().bucket(bucketName).build();
                client.makeBucket(bucketArgs);
           }
        }catch (Exception e){
            log.info("MinIO 创建Bucket失败：{}",e);
            throw  new MyException(ErrorEnums.MinIO_ERROR);

        }

    }
    public String uploadFile(MultipartFile file, String bucketName,String fileName){
        String path=fileName+"."+ FileUtil.getSuffix(file.getOriginalFilename());
        if(!StrUtil.containsAny(path,".png",".jpg",".jpeg",".rar",".zip")){
            log.info("格式错误: {}",FileUtil.getSuffix(path));
            throw new MyException(ErrorEnums.CHECK_ERROR);
        }
        try {
            client.putObject( PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(path)
                    .contentType(file.getContentType()).build());
        } catch (Exception e){
            log.info("[MinIO]:上传文件失败 {}",e);
            throw new MyException(ErrorEnums.MinIO_ERROR);
        }
        return path;
    }

    public  GetObjectResponse downloadFile(String buckName,String path){
        GetObjectArgs args=GetObjectArgs.builder().bucket(buckName).object(path).build();
        try {
           return client.getObject(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}