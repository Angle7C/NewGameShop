package com.example.newgameshop.service.Impl;

import com.example.newgameshop.entity.BuyCar;
import com.example.newgameshop.mapper.BuyCarMapper;
import com.example.newgameshop.service.BuyCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BuyCarServiceImpl implements BuyCarService {
    @Resource
    private BuyCarMapper buyCarMapper;

    public BuyCar findBuyCar(Integer id){
        BuyCar buyCar=buyCarMapper.findBuyCar(id);
        return buyCar;}

    public List<BuyCar> findUserId(Integer id){
        List<BuyCar> buyCarList=buyCarMapper.findUserId(id);
        return buyCarList;}

    public Boolean addBuyCar(BuyCar buyCar){
        buyCarMapper.addBuyCar(buyCar);
        return true;}

    public Boolean deleteBuyCar(BuyCar buyCar){
        buyCarMapper.deleteBuyCar(buyCar);
        return true;}

    public Boolean updateBuyCar(BuyCar buyCar){
        buyCarMapper.updateBuyCar(buyCar);
        return true;}
}
