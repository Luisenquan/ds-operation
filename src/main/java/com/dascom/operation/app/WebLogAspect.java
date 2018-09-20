package com.dascom.operation.app;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



@Aspect
@Component
public class WebLogAspect {
	private Logger logger =  LogManager.getLogger(getClass());
	
	/*@Autowired
	private Logs logs;*/
	
	@Pointcut("execution(public * com.dascom.operation.controller..*.*(..))")
	public void webLog() {
	}
	
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		logger.info("************************请求开始*******************************");
		//logs.info("************************请求开始*******************************", null);
		logger.info("请求地址 : " + request.getRequestURL().toString());
		//logs.info("请求地址 : " + request.getRequestURL().toString(), request.getRemoteAddr());
		logger.info("请求方法 : " + request.getMethod());
		//logs.info("请求方法 : " + request.getMethod(), request.getRemoteAddr());
		logger.info("IP : " + request.getRemoteAddr());
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			logger.info("参数:{},值:{}", name, request.getParameter(name));
			//logs.info("参数:"+name+",值:"+request.getParameter(name), request.getRemoteAddr());
		}
	}
	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		//logger.info("响应结果 : " + ret);
		logger.info("************************请求结束*******************************");
		//logs.info("************************请求结束*******************************", null);
	}
}
