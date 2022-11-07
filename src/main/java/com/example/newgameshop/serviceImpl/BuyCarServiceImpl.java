package com.example.newgameshop.serviceImpl;

import com.gameshop.entity.BuyCar;
import com.gameshop.entity.Game;
import com.gameshop.entity.User;
import com.gameshop.mapper.BuyCarMapper;
import com.gameshop.service.BuyCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BuyCarServiceImpl implements BuyCarService {
    private BuyCarMapper buyCarMapper;
    public BuyCarMapper getBuyCarMapper(){ return buyCarMapper; }
    @Autowired
    public void setBuyCarMapper(BuyCarMapper buyCarMapper) { this.buyCarMapper=buyCarMapper; }
    public BuyCar findBuyCar(Integer id) { return buyCarMapper.findBuyCar(id); }
    public List<BuyCar> findUserId(Integer id) { return buyCarMapper.findUserId(id); }
    public void addBuyCar(BuyCar buyCar) { buyCarMapper.addBuyCar(buyCar); }
    public void deleteBuyCar(BuyCar buyCar) { buyCarMapper.deleteBuyCar(buyCar); }
    public void updateBuyCar(BuyCar buyCar) { buyCarMapper.updateBuyCar(buyCar); }
}
