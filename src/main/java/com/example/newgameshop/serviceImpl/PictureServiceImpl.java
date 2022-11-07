package com.example.newgameshop.serviceImpl;

import com.gameshop.entity.Picture;
import com.gameshop.mapper.PictureMapper;
import com.gameshop.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    private PictureMapper pictureMapper;
    public  PictureMapper getPictureMapper(){ return pictureMapper; }
    @Autowired
    public void setPictureMapper(PictureMapper pictureMapper){ this.pictureMapper=pictureMapper; }
    public List<Picture> findGameId(Integer id) { return pictureMapper.findGameId(id); }
    public void addPicture(Picture picture) { pictureMapper.addPicture(picture); }
    public void updatePicture(Picture picture) { pictureMapper.updatePicture(picture); }
    public void deletePicture(Picture picture) { pictureMapper.deletePicture(picture); }

    @Override
    public void deletePicture(Integer gameId) {
        pictureMapper.deletePictures(gameId);
    }
}
