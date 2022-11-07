package com.example.newgameshop.until;

public class Files {
    public static String getsuff(String fileName){
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        // 解析后缀
        // aa.jpg  aa.bb.jpg  bb.cc.aa.png
        if (fileName.lastIndexOf(".") == -1) {
            return null;
        }
        // 截取字符串
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return suffix;
    }
}
