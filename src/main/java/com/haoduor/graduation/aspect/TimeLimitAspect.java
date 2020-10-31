package com.haoduor.graduation.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import com.haoduor.graduation.annotation.TimeLimit;
import com.haoduor.graduation.model.Period;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

@Aspect
@Component
public class TimeLimitAspect {

    @Resource(name = "periodMap")
    private Map<String, Period> periodMap;

    @Pointcut("@annotation(com.haoduor.chatdemo.annotation.TimeLimit)")
    public void timePointCut() {}

    @Around("timePointCut()")
    public Object doAround(ProceedingJoinPoint pjp) {
        //获取方法签名
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        //获取切入方法的对象
        Method method = signature.getMethod();
        //获取方法上的Aop注解
        TimeLimit timeLimit = method.getAnnotation(TimeLimit.class);

        Class returnType = method.getReturnType();
        Object ret = ReflectUtil.newInstance(returnType);


        return null;
    }

}
