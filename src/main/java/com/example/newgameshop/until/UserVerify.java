package com.example.newgameshop.until;

import com.sun.javafx.collections.MappingChange;

import javax.servlet.http.HttpSession;
import java.util.*;
public class UserVerify {
    public static  Integer verify( HttpSession session){
        return (Integer)session.getAttribute("userId");
    }
}
