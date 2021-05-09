package com.dahuaboke.mvc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author dahua
 * @Date 2021/5/6 9:22
 * @Description mvc
 */
@Component
@Aspect
public class MvcRequestMappingAspect {

    /**
     * 如果切点和注解在同一个包下可以直接使用
     *
     * @Pointcut("@annotation(MvcRequestMapping)")
     */
    @Pointcut("@annotation(com.dahuaboke.mvc.anno.MvcRequestMapping)")
    private void point() {
    }

    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        return joinPoint.proceed(args);
    }

}
