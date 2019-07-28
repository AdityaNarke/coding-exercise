package com.aditya.exercise.aop;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeManagementAspect {

	private Logger logger = LoggerFactory.getLogger(getClass());

	  @Around("controllerLogging() || serviceLogging() || repositoryLogging()")
	  public Object  baseControllerLogs(ProceedingJoinPoint pjp) throws Throwable{
		    long start = System.nanoTime();
	        Object retval = pjp.proceed();
	        long end = System.nanoTime();
	        String methodName = pjp.getSignature().getName();
	        logger.info("Execution of " + methodName + " took " + 
	          TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
	        return retval;
	  }
	  
	  @AfterThrowing(pointcut = "execution(* com.aditya.exercise..*Controller.*(..)) ||"
	  		+ "execution(* com.aditya.exercise..*Service.*(..)) ||"
	  		+ "execution(* com.aditya.exercise..*Repository.*(..))", 
			  throwing = "error")
	  public void afterExpectionThrown(JoinPoint jp, Throwable error) {
		  logger.info("Method Signature: "  + jp.getSignature());  
		  logger.error("Exception: "+error);
	  }
	  
	  @Pointcut("execution(* com.aditya.exercise..*Controller.*(..))")
	  public void controllerLogging() {
	  }
	  
	  
	  @Pointcut("execution(* com.aditya.exercise..*Service.*(..))")
	  public void serviceLogging() {
	  }
	  
	  @Pointcut("execution(* com.aditya.exercise..*Repository.*(..))")
	  public void repositoryLogging() {
	  }
}
