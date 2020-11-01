package com.haoduor.graduation.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import com.haoduor.graduation.annotation.TimeLimit;
import com.haoduor.graduation.model.Period;
import com.haoduor.graduation.vo.BaseMessage;
import com.haoduor.graduation.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class TimeLimitAspect {

    @Resource(name = "periodMap")
    private Map<String, Period> periodMap;

    @Pointcut("@annotation(com.haoduor.graduation.annotation.TimeLimit)")
    public void timePointCut() {}

    @Around("timePointCut()")
    public Object doAround(ProceedingJoinPoint pjp) {
        //获取方法签名
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        //获取切入方法的对象
        Method method = signature.getMethod();
        //获取方法上的Aop注解
        TimeLimit timeLimit = method.getAnnotation(TimeLimit.class);
        String name = timeLimit.periodName();

        Class returnType = method.getReturnType();
        Object ret = ReflectUtil.newInstance(returnType);
        Message m = null;

        if (ret instanceof Message) {
            m = (Message) ret;
        } else {
            m = new BaseMessage();
        }

        Period period = null;
        if (periodMap.containsKey(name)) {
            period = periodMap.get(name);
        } else {
            log.error("无效阶段控制名称 {}", name);
            return m.invoke(101, "无效阶段控制名称");
        }

        Date now = DateUtil.date();

        if (period.getStartTime().after(now)) {
            return m.invoke(102, "阶段开始之前");
        }

        if (period.getEndTime().before(now)) {
            return m.invoke(102, "阶段结束之后");
        }

        Object res = null;
        try {
            res = pjp.proceed();
        } catch (Throwable throwable) {
            log.error("阶段控制出现异常");
            throwable.printStackTrace();
        }

        return res;
    }

}
