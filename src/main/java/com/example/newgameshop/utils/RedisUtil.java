package com.example.newgameshop.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@Getter
public class RedisUtil {
    @Resource
    private  StringRedisTemplate redis;

    public  void setValue(String key, String value, long times, TimeUnit unit){
        if(times<=0){
            redis.opsForValue().set(key, value);
        }else{
            redis.opsForValue().set(key, value, times, unit);
        }

    }
    public String getValue(String key){
       return redis.opsForValue().get(key);
    }
}
