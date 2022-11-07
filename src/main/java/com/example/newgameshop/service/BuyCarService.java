package com.example.newgameshop.service;

import com.example.newgameshop.entity.BuyCar;

import java.util.List;

public interface BuyCarService {
    default BuyCar findBuyCar(Integer id){return null;}
    default List<BuyCar> findUserId(Integer id){return null;}
    default Boolean addBuyCar(BuyCar buyCar){return false;}
    default Boolean deleteBuyCar(BuyCar buyCar){return false;}
    default Boolean updateBuyCar(BuyCar buyCar){return false;}
}
