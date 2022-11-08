package com.example.newgameshop.advice;


import cn.hutool.core.util.ObjectUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {
         
	private final static Logger logger =LoggerFactory.getLogger(HttpAspect.class);
	
	@Pointcut("execution(public * com.example.newgameshop.controller.*.*(..))")
	public void log() {}
	
	@Before("log()")
	public void logbefore(JoinPoint point) {
		ServletRequestAttributes attributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		assert attributes != null;
		HttpServletRequest request =attributes.getRequest();
		logger.info("url={}",request.getRequestURI());
		logger.info("method={}",request.getMethod());
		logger.info("IP:PORT={}",request.getRemoteAddr());
		logger.info("Class_Method={}",point.getSignature().getDeclaringTypeName()+"."+point.getSignature().getName());
		
		Object[] args=point.getArgs();
		StringBuilder argsStr= new StringBuilder();
		for(Object arg : args) {
			argsStr.append(arg.toString());
		}
		logger.info("args={}", argsStr.toString());
	}
	
	@AfterReturning(pointcut="log()",returning="ret")
	public void logAfterRet(Object ret) {
		if(ObjectUtil.isEmpty(ret)){
			logger.info("没有返回值");
		}else{
			logger.info("response={}",ret.toString());
		}
	}
	
	
	
	
	
}
