package com.example.newgameshop.service.impl;

import com.example.newgameshop.entity.Picture;
import com.example.newgameshop.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PictureServiceImpl implements PictureService {
    @Override
    public List<Picture> findGameId(Integer id) {
        return null;
    }

    @Override
    public void addPicture(Picture picture) {

    }

    @Override
    public void updatePicture(Picture picture) {

    }

    @Override
    public void deletePicture(Picture picture) {

    }

    @Override
    public void deletePicture(Integer gameId) {

    }
}
