package com.learning.Spring_Boot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Logging and Profiling - Most use cases of AOP
 * Profiling in the context of AOP refers to measuring the performance of methods, such as tracking execution time. It helps identify bottlenecks or performance issues in the application.
 *
 */
@Aspect
@Component
public class RatingAspect {
    private static final Logger logger = LoggerFactory.getLogger(RatingAspect.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Pointcut("execution(* com.learning.Spring_Boot.redis.RatingController.*(..))")
    // Defines a pointcut that matches the execution of any public method in classes under com.example.service package
    public void allServiceMethods() {}


    // Advice that runs before any method matched by allServiceMethods()
    @Before("allServiceMethods()")
    public void beforeAdvice(JoinPoint joinPoint){
        String formattedDateTime = LocalDateTime.now().format(formatter);
        logger.info("{} {} at {}", joinPoint.getSignature(),"started at", formattedDateTime);
    }

    // Advice that runs after any method matched by allServiceMethods()
    @After("allServiceMethods()")
    public void afterAdvice(JoinPoint joinPoint){
        String formattedDateTime = LocalDateTime.now().format(formatter);
        logger.info("{} {} at {}", joinPoint.getSignature(),"ended at", formattedDateTime);
    }

    // Advice that runs after returning from any method matched by allServiceMethods()
    @AfterReturning(pointcut = "allServiceMethods()",returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("Method returned: " + result);
    }

    @AfterThrowing(pointcut = "allServiceMethods()",throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Exception e) {
        System.out.println("Exception thrown " + e.getMessage());
    }

    @Around("allServiceMethods() && @annotation(com.learning.Spring_Boot.common.Idempotent)")
    // Advice that runs before and after the execution of methods matched by the pointcut
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Before and after method: " + joinPoint.getSignature().getName());
        return joinPoint.proceed(); // Proceed with the next advice or target method invocation
    }

    @Before("allServiceMethods() && @annotation(com.learning.Spring_Boot.common.Idempotent)")
    // advice that runs before for all the pointcut method which has the annotation of Idempotent (Custom annotation)
    public void beforeAdviceForCustomAnnotationMethod(JoinPoint joinPoint) throws InterruptedException {
        String formattedDateTime = LocalDateTime.now().format(formatter);
        logger.info("{} {} at {}", "Custom Annotation method","started at", formattedDateTime);
    }
}
