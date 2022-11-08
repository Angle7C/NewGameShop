package com.example.newgameshop.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
    public Picture findGameId(Integer id) {
        List<Picture> game = pictureMapper.findGameId(id);
        if(ObjectUtil.isEmpty(game)){
            return null;
        }else{
            return game.get(0);
        }
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
