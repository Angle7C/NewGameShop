package com.example.newgameshop.untils;

import javax.servlet.http.HttpSession;

public class UserVerify {
    public static  Integer verify( HttpSession session){
        return (Integer)session.getAttribute("userId");
    }
}
