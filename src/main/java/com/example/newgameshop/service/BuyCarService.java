package com.example.newgameshop.service;

import com.gameshop.entity.BuyCar;

import java.util.List;

public interface BuyCarService {
    public BuyCar findBuyCar(Integer id);
    public List<BuyCar> findUserId(Integer id);
    public void addBuyCar(BuyCar buyCar);
    public void deleteBuyCar(BuyCar buyCar);
    public void updateBuyCar(BuyCar buyCar);
}
