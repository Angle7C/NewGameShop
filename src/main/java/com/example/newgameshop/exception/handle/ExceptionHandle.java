package com.example.newgameshop.exception.handle;

import com.example.newgameshop.advice.HttpAspect;
import com.example.newgameshop.entity.JsonResult;
import com.example.newgameshop.exception.MyException;
import com.example.newgameshop.exception.enums.ErrorEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {
	
	private final static Logger logger =LoggerFactory.getLogger(HttpAspect.class);
	
	@ExceptionHandler(value=Exception.class)
	public JsonResult<?> handle(Exception e) {
		logger.error("【系统异常】：",e);
		return JsonResult.fail(ErrorEnums.UNKNOWN_ERROR.getCode(), ErrorEnums.UNKNOWN_ERROR.getMessage());
	}
	
	@ExceptionHandler(value=MyException.class)
	public JsonResult<?> myhandle(MyException e) {
		logger.error("【操作异常】：",e);
		return JsonResult.fail(e.getCode(),e.getMessage());
	}
	
	

}
