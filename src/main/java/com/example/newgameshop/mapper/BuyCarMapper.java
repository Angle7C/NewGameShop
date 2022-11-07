package com.example.newgameshop.mapper;

import com.gameshop.entity.BuyCar;

import java.util.List;

public interface BuyCarMapper {
    public BuyCar findBuyCar(Integer id);
    public List<BuyCar> findUserId(Integer id);
    public void addBuyCar(BuyCar buyCar);
    public void deleteBuyCar(BuyCar buyCar);
    public void updateBuyCar(BuyCar buyCar);

}
