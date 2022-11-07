package com.example.newgameshop.exception;
 
import com.example.newgameshop.exception.enums.ErrorEnums;

public class MyException extends RuntimeException {
     
    private Integer code;
 
    public Integer getCode() {
        return code;
    }
 
    public void setCode(Integer code) {
        this.code = code;
    }
     
    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }
     
    public MyException(ErrorEnums enums) {
        super(enums.getMessage());
        this.code = enums.getCode();
    }
     
    public MyException(ErrorEnums enums,String message) {
        super(message);
        this.code = enums.getCode();
    }
 
}