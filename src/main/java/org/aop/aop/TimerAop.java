package org.aop.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Aspect
@Component //Bean 어노테이션으로는 클래스단위로 등록할 수 없음
public class TimerAop {
    // method에서 bean을 사용할 수 있고
    // configuration은 한 클래스에서 여러 bean을 등록 가능
    @Pointcut("execution(* org.aop.controller..*.*(..))")
    private void cut(){}

    @Pointcut("@annotation(org.aop.annotation.Timer)")
    private void enableTime(){}

    @Around("cut() && enableTime()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = pjp.proceed();
        stopWatch.stop();
        System.out.println("total time: " + stopWatch.getTotalTimeSeconds());
    }



}
