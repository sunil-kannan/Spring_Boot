//package com.learning.Spring_Redis.aop;
//
//import com.learning.Spring_Redis.customAnnotation.Secured;
//import org.springframework.stereotype.Component;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * This will be used when you are integrating with the spring security.
// * Need to give those annotation in the method layer = @Secured(roles = {"ADMIN"}) or @Secured(roles = {"USER", "ADMIN"})
// */
//@Aspect
//@Component
//public class SecurityAspect {
//
//    @Before("@annotation(secured)")
//    public void checkAccess(JoinPoint joinPoint, Secured secured) throws Throwable {
//        // Simulate getting the current user's roles (in a real application, you'd retrieve this from the session, security context, etc.)
//        List<String> userRoles = getCurrentUserRoles();
//
//        // Check if the user has any of the required roles
//        boolean hasAccess = Arrays.stream(secured.roles())
//                .anyMatch(userRoles::contains);
//
//        if (!hasAccess) {
//            throw new SecurityException("You do not have permission to access this method");
//        }
//    }
//
//    private List<String> getCurrentUserRoles() {
//        // Simulated user roles, replace with actual user role retrieval logic
//        return List.of("USER", "ADMIN");
//    }
//}