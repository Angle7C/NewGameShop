package com.example.newgameshop.entity;

public class Score {
    private Integer scoreId;
    private Integer gameId;
    private Integer gameStore;

    public Score() {
    }

    public Score(Integer scoreId, Integer gameId, Integer gameStore) {
        this.scoreId = scoreId;
        this.gameId = gameId;
        this.gameStore = gameStore;
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
