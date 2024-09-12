package com.learning.Spring_Boot.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  // This specifies that the annotation can be applied to methods
@Retention(RetentionPolicy.RUNTIME)  // This makes the annotation available at runtime
public @interface Idempotent {
}
