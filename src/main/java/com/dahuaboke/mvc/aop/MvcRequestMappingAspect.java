package com.dahuaboke.mvc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

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
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        System.out.println(1);
        Object[] args = joinPoint.getArgs();
        for(Object obj:args){
        }
        return joinPoint.proceed(args);
    }

}
