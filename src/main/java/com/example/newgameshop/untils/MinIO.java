package com.example.newgameshop.untils;


import cn.hutool.core.util.ObjectUtil;
import com.example.newgameshop.exception.MyException;
import com.example.newgameshop.exception.enums.ErrorEnums;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

    public void uploadFile(List<MultipartFile> name, String bucketName){


    }
}