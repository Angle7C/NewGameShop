package com.example.newgameshop.mapper;


import com.example.newgameshop.entity.Picture;

import java.util.List;


public interface PictureMapper {
    public List<Picture> findGameId(Integer id);
    public void addPicture(Picture picture);
    public void updatePicture(Picture picture);
    public void deletePicture(Picture picture);
    public void deletePictures(Integer gameId);
}
