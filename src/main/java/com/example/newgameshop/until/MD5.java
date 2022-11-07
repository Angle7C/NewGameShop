package com.example.newgameshop.until;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public  class MD5 {
    public static String toMD5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }
}
