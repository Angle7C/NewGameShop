package com.example.newgameshop.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.newgameshop.entity.FileDomain;
import com.example.newgameshop.service.UploadService;
import com.example.newgameshop.untils.MinIO;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Setter(onMethod_ ={@Resource} )
public class UploadServiceImpl implements UploadService {
    private MinIO minIO;
    @Override
    public String multiFileUpload(FileDomain fileDomain,String buckName) {
        List<MultipartFile> files=fileDomain.getFiles();
        if(FileUtil.getSuffix(files.get(0).getOriginalFilename()).equals(".rar")){
            
        }
        return null;
    }
}
