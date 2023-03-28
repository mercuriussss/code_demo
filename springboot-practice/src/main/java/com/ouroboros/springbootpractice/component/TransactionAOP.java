package com.ouroboros.springbootpractice.component;

import org.springframework.stereotype.Component;

@Component
public class TransactionAOP {
    public void openTransition(){
        System.out.println("开启事务");
    }
    public void closeTransition(){
        System.out.println("关闭事务");
    }
}
