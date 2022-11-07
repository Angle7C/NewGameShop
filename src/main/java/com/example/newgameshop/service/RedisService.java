package com.example.newgameshop.service;

import org.springframework.stereotype.Component;

@Component
public interface RedisService {
    void set(String key,String value);
    String get(String key);
    boolean expire(String key,Long expire);
    void remove(String key);
    Long increment(String key,Long delta);
}
