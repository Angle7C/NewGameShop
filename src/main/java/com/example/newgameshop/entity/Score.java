package com.example.newgameshop.entity;

import org.apache.ibatis.type.Alias;

@Alias("Score")
public class Score {
    private Integer scoreId;
    private Integer gameId;
    private Integer gameStore;
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Score() {
    }

    public Score(Integer scoreId, Integer gameId, Integer gameStore,Integer userId) {
        this.scoreId = scoreId;
        this.gameId = gameId;
        this.gameStore = gameStore;
        this.userId=userId;
    }

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getGameStore() {
        return gameStore;
    }

    public void setGameStore(Integer gameStore) {
        this.gameStore = gameStore;
    }
}
