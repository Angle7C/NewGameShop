package com.example.newgameshop.service;

import com.example.newgameshop.entity.FileDomain;
import io.minio.GetObjectResponse;

public interface UploadService {

    String multiFileUpload(FileDomain fileDomain,String bucketName,Integer gameId);
    GetObjectResponse downloadFile(String path, String bucketName);
}
