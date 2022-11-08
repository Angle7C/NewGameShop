package com.example.newgameshop.entity;

import org.apache.ibatis.type.Alias;

@Alias("User")
public class User {
      private Integer userId;
      private String userName;
      private String userPwd;
      private String email;
      private Integer role;
      private Double money;

    public User() {
    }

    public User(Integer userId, String userName, String userPwd, String email, Integer role, Double money) {
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.email = email;
        this.role = role;
        this.money = money;
    }
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
