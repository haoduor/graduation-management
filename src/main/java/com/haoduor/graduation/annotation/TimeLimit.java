package com.haoduor.graduation.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TimeLimit {

    @AliasFor("value")
    String periodName() default "";
}
