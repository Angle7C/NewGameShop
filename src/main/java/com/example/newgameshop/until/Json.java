package com.example.newgameshop.until;

import com.google.gson.JsonParser;

public class Json {
    public final static JsonParser json=new JsonParser();
    public static String toAttribute(String str,String Attribute){
            return json.parse(str).getAsJsonObject().get(Attribute).getAsString();
    }
}
