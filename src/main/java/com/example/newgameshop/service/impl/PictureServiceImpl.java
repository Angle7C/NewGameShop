package com.example.newgameshop.service.impl;

import com.example.newgameshop.entity.Picture;
import com.example.newgameshop.mapper.PictureMapper;
import com.example.newgameshop.service.PictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class PictureServiceImpl implements PictureService {
    @Resource
    private PictureMapper pictureMapper;
    @Override
    public List<Picture> findGameId(Integer id) {
        return pictureMapper.findGameId(id);
    }

    @Override
    public void addPicture(Picture picture) {
        pictureMapper.addPicture(picture);
    }

    @Override
    public void updatePicture(Picture picture) {
        pictureMapper.updatePicture(picture);
    }

    @Override
    public void deletePicture(Picture picture) {
        pictureMapper.deletePicture(picture);
    }

    @Override
    public void deletePicture(Integer gameId) {
        pictureMapper.deletePictures(gameId);
    }
}
