package com.ouroboros.springbootpractice.component;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TransitionAOP {

    @Pointcut("execution(* com.ouroboros.springbootpractice.service.*.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void openTransition() {
        System.out.println("开启事务");
    }

    @After("pointCut()")
    public void closeTransition() {
        System.out.println("关闭事务");
    }
}