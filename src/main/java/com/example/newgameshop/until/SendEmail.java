package com.example.newgameshop.until;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
    public static String sendTextMail(String strMail) throws EmailException{
        String strTitle="GameShop验证码";
        System.out.println(strMail);
        SimpleEmail mail = new SimpleEmail();
            // 设置邮箱服务器信息
            Random random=new Random();
           String ans=String.format(Integer.valueOf(random.nextInt(10000)).toString(), "%04d");
            System.out.println(ans);
            mail.setSslSmtpPort("25");
            mail.setHostName("smtp.163.com");
            // 设置密码验证器
            mail.setAuthentication("15305067103@163.com", "xc396655406");
            // 设置邮件发送者
            mail.setFrom("15305067103@163.com");
            // 设置邮件接收者
            mail.addTo(strMail);
            // 设置邮件编码
            mail.setCharset("UTF-8");
            // 设置邮件主题
            mail.setSubject(strTitle);
            // 设置邮件内容
            mail.setMsg(ans);
            // 设置邮件发送时间
            mail.setSentDate(new Date());
            // 发送邮件
            mail.send();
            return ans;
    }
}
