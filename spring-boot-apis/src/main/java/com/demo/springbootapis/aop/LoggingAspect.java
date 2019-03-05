package com.demo.springbootapis.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

	/* 
	 * Pointcut that matches all respositories, services, and Web Rest endpoint
	 */
	@Pointcut("within(@org.springframework.stereotype.Service *)" + 
			" || within(@org.springframework.stereotype.Repository *)" + 
			" || within(@org.springframework.web.bind.annotation.RestController *)"
			)
	public void springBeanPointcut() {
	}


	@Pointcut("within(com.demo.springbootapis.controller.*) ||"
		+ " within(com.demo.springbootapis.repository.*) ||"
		+ " within(com.demo.springbootapis.service.*) ||" 
		+ " within(com.demo.springbootapis.util.*)")
	public void applicationPackagePointcut() {
	}
	
	@Pointcut("within(com.demo.springbootapis.security..*) &&"
			+ "!within(com.demo.springbootapis.security.JwtAuthorizationFilter)")
	public void applicationSecurityPointcut() {
		
	}
	
	@Around("applicationPackagePointcut() || applicationSecurityPointcut()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable{
		if (log!=null && log.isDebugEnabled()) {
			log.debug("Enter {}.{} with argument[s] = {}",
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(),
					Arrays.toString(joinPoint.getArgs()));
		}
		
		Object res = joinPoint.proceed();
		if (log != null &&  log.isDebugEnabled()) {
			log.debug("Exit {}.{} with result = {}",
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(),
					res);
		}
		
		return res;
    }
	
	@AfterThrowing(pointcut="applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		log.error("Exception in {}.{}() cause = \'{}\' and exception = \'{}\'", 
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(),
				e.getCause() != null ? e.getCause() : "NULL",
				e.getMessage(),
				e);
	}
	
	
}
