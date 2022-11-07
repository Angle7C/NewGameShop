package com.example.newgameshop.entity;

public class BuyCar {
    private Integer buyCarId;
    private Integer gameId;
    private Integer userId;

    public BuyCar() {
    }

    public BuyCar(Integer buyCarId, Integer gameId, Integer userId) {
        this.buyCarId = buyCarId;
        this.gameId = gameId;
        this.userId = userId;
    }

    public Integer getBuyCarId() {
        return buyCarId;
    }

    public void setBuyCarId(Integer burCarId) {
        this.buyCarId = burCarId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
