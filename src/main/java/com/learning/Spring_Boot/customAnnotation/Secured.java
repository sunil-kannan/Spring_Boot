package com.learning.Spring_Boot.customAnnotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Secured {
    String[] roles() default {}; // Define roles that are allowed to access the method
}
