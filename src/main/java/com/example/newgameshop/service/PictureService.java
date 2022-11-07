package com.example.newgameshop.service;

import com.gameshop.entity.Picture;

import java.util.List;

public interface PictureService {
    public List<Picture> findGameId(Integer id);
    public void addPicture(Picture picture);
    public void updatePicture(Picture picture);
    public void deletePicture(Picture picture);
    public void deletePicture(Integer gameId);
}
