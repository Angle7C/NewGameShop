package com.example.newgameshop.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JsonResult<T> {

    public static final int SUCESS=0;
    private int state;
    private String message="";
    private T data;
    private List<T> datas;
    public JsonResult(String message) {
        this.state=SUCESS;
        this.message=message;
    }

    public JsonResult(int state, String message) {
        this.state=state;
        this.message=message;
    }

    public JsonResult(T data) {
        this.state=SUCESS;
        this.data=data;
    }

    public JsonResult(String message, T data) {
        this.state=SUCESS;
        this.message=message;
        this.data=data;
    }

    public JsonResult(int state, String message, T data) {
        this.state=state;
        this.message=message;
        this.data=data;
    }

    public JsonResult(List<T> datas) {
        this.state=SUCESS;
        this.datas=datas;
    }

    public JsonResult(String message, List<T> datas) {
        this.state=SUCESS;
        this.message=message;
        this.datas=datas;
    }

    public JsonResult(int state, String message, List<T> datas) {
        this.state=state;
        this.message=message;
        this.datas=datas;
    }
    public static JsonResult<String> fail(int code,String message){
        JsonResult<String> stringJsonResult = new JsonResult<String>();
        stringJsonResult.setMessage(message);
        stringJsonResult.setState(code);
        return stringJsonResult;
    }
}
