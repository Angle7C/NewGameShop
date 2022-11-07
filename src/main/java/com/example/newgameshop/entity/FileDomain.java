package com.example.newgameshop.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileDomain {
    private List<String> description;
    private List<MultipartFile> myfile;

    public FileDomain() {
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }
    public List<MultipartFile> getMyfile() {
        return myfile;
    }

    public void setMyfile(List<MultipartFile> myfile) {
        this.myfile = myfile;
    }
}
