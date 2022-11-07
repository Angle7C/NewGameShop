package com.example.newgameshop.entity;

public class Picture {
    private Integer gameId;
    private String path;
    private Integer pictureId;

    public Picture() {
    }

    public Picture(Integer gameId, String path, Integer pictureId) {
        this.gameId = gameId;
        this.path = path;
        this.pictureId = pictureId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }
}
