package org.aop.aop;

import org.aop.dto.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Aspect
@Component
public class DecodeAop {
    @Pointcut("execution(* org.aop.controller..*.*(..))")
    private void cut(){}

    @Pointcut("@annotation(org.aop.annotation.Decode)")
    private void enableDecode(){}


    @Before("cut() && enableDecode()")
    public void before(JoinPoint jp) throws UnsupportedEncodingException {
        Object[] args = jp.getArgs();
        for(Object arg: args){
            if(arg instanceof User) {
                User user = (User)arg;
                String base64Email = user.getEmail();
                String email = new String(Base64.getDecoder().decode(base64Email), "UTF-8");
                user.setEmail(email);
            }
        }
    }

    @AfterReturning(value = "cut() && enableDecode()", returning = "returnObj")
    public void afterReturn(JoinPoint jp, Object returnObj){
        if(returnObj instanceof  User){
            User user = (User) returnObj;
            //(User)arg;
            String email = user.getEmail();
            String base64Email = Base64.getEncoder().encodeToString(email.getBytes());
            user.setEmail(base64Email);
        }
    }
}
