package com.example.newgameshop.service;

import com.example.newgameshop.entity.FileDomain;

public interface UploadService {

    String multiFileUpload(FileDomain fileDomain,String bucketName);
}
