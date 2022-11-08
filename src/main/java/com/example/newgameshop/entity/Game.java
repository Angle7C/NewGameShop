package com.example.newgameshop.entity;

import org.apache.ibatis.type.Alias;

@Alias("Game")
public class Game {
    private Integer gameId;
    private String gameName;
    private Float gameValue;
    private String message;
    private Integer userId;
    private String gameType;
    private Integer gameStore;
    private String path;

    public Game() {
    }

    public Game(Integer gameId, String gameName, Float gameValue, String message, Integer userId, String gameType, Integer gameStore, String path) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameValue = gameValue;
        this.message = message;
        this.userId = userId;
        this.gameType = gameType;
        this.gameStore = gameStore;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Integer getGameStore() {
        return gameStore;
    }

    public void setGameStore(Integer gameStore) {
        this.gameStore = gameStore;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Float getGameValue() {
        return gameValue;
    }

    public void setGameValue(Float gameValue) {
        this.gameValue = gameValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
