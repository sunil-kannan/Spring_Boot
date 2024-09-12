//package com.learning.Spring_Redis.aop;
//
//import com.learning.Spring_Redis.customException.RatingRollbackException;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import java.util.Objects;
//
///**
// * Transaction Management - in an enterprise application, you can manage the transactional requests using AOP.
// * For example, using the *Around *advice, you can wrap the request processing method and if the method fails for some reason,
// * in your Advice, you can roll back the particular transaction as below.
// */
//@Component
//@Aspect
//public class TransactionManagementAspect {
//    private final PlatformTransactionManager transactionManager;
//
//    public TransactionManagementAspect(PlatformTransactionManager transactionManager) {
//        this.transactionManager = transactionManager;
//    }
//
//    @Pointcut("execution(* com.learning.Spring_Redis.controller.RedisController.*(..)) " +
//            "&& @annotation(com.learning.Spring_Redis.common.Idempotent)")
//    public void transactionMethods() {}
//
//    @Around("transactionMethods()")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
//        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//
//        // Start a new transaction
//        TransactionStatus status = Objects.requireNonNull(transactionTemplate.getTransactionManager()).getTransaction(definition);
//        Object result = null;
//
//        try {
//            result = joinPoint.proceed();
//            // Commit the transaction if method succeeds
//            transactionTemplate.getTransactionManager().commit(status);
//        } catch (Throwable ex) {
//            // Rollback the transaction if an exception is thrown
//            transactionTemplate.getTransactionManager().rollback(status);
//            throw new RatingRollbackException("Not found");
//        }
//
//        return "result";
//    }
//}
