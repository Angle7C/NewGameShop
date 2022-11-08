package com.example.newgameshop.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.example.newgameshop.exception.MyException;
import com.example.newgameshop.exception.enums.ErrorEnums;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RegisteredEmailUtil {
    private static final String symbols="01234564789ADBCGPOLKJ";
    private static final Random random=new SecureRandom();

    private static MailAccount account=new MailAccount();
    static {
        account.setHost("smtp.163.com");
        account.setPort(25);
        account.setAuth(true);
        account.setFrom("15305067103@163.com");
        account.setUser("15305067103@163.com");
        account.setPass("AFVQHCTJDIUDRVLE");
    }
    @Autowired
    private  RedisUtil redisUtil;
    public  boolean registerCode(String to){
        if (!Validator.isEmail(to)){
            throw new  MyException(ErrorEnums.CHECK_ERROR.getCode(),"邮箱格式错误");
        }
        String code=getCode();
        log.info("[emial code]:{}",code);
        String ans= MailUtil.send(account,to, "验证码测试", code, false);
        redisUtil.setValue(to,code,10, TimeUnit.MINUTES);
        return true;
    }
    public   String getCode(){
        //生成六位验证码
        char[] codeTemp=new char[6];
        for(int i=0;i<6;i++){
            codeTemp[i]=symbols.charAt(random.nextInt(symbols.length()));
        }
        return new String(codeTemp);
    }
}
