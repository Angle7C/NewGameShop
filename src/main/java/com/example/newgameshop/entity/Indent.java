package com.example.newgameshop.entity;

import org.apache.ibatis.type.Alias;

import java.util.Date;
@Alias("indent")
public class Indent {
    private Integer userId;
    private Integer orderId;
    private Integer gameId;
    private Float value;
    private Date  date;

    public Indent() {
    }

    public Indent(Integer userId, Integer orderId, Integer gameId, Float value, Date date) {
        this.userId = userId;
        this.orderId = orderId;
        this.gameId = gameId;
        this.value = value;
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
