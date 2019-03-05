package com.demo.springbootapis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogExecutionTimeAspect {
	
	@Pointcut("@annotation(com.demo.springbootapis.aop.LogExecutionTime)")
	public void loggExecutionAnnotationPointCut(){}
	
	@Pointcut("@target(org.springframework.stereotype.Service)")
	public void serviceMethods() {}
	
	@Around("loggExecutionAnnotationPointCut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
		long start = System.currentTimeMillis();
		
		Object proceed = joinPoint.proceed();
		long executionTime = System.currentTimeMillis() - start;
		
		log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
		return proceed;
	}
}
