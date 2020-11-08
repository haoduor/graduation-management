package com.haoduor.graduation.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeCountAspect {

    @Pointcut("@annotation(com.haoduor.graduation.annotation.TimeCount)")
    public void PointCut() {}

    @Around("PointCut()")
    public Object doAround(ProceedingJoinPoint pjp) {
        TimeInterval time = DateUtil.timer();
        time.start();

        Object res = null;
        try {
            res = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        log.info("方法消耗 {} {}", pjp.getSignature().getName(), DateUtil.formatBetween(time.interval()));

        return res;
    }
}
