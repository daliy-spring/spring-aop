package org.aop.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component //spring에서 관리해줘!
public class PrameterAop {

    @Pointcut("execution(* org.aop.controller..*.*(..))")
    private void cut(){}

    @Before("cut()")
    public void before(JoinPoint jp){
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName());
        Object[] args = jp.getArgs();
        for(Object obj : args) {
            System.out.println("type :" + obj.getClass().getSimpleName());
            System.out.println("value :" + obj);
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterReturn(JoinPoint jp, Object returnObj){
        System.out.println("return obj");
        System.out.println(returnObj);
    }

}
