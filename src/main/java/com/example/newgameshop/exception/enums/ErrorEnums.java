package com.example.newgameshop.exception.enums;
 
public enum ErrorEnums {
     
    //异常定义列表
    UNKNOWN_ERROR(1,"未知异常"),
    FIND_NULL(0,"未查到任何数据"),
    FIND_ERROR(101,"查询异常"),
    ADD_ERROR(102,"添加异常"),
    EDIT_ERROR(103,"修改异常"),
    REMOVE_ERROR(104,"删除异常"),
    MinIO_ERROR(105,"MinIO异常"),
    LOGIN_ERROR(0,"登录异常"),
    CHECK_ERROR(110,"格式异常");
     
    private Integer code;
    private String message;
    ErrorEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    private ErrorEnums(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}