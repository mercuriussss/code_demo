package com.ouroboros.springbootpractice.service.impl;

import org.springframework.stereotype.Component;

@Component
public class NonInterfaceUser {

    public void insertUser(){
        System.out.println("插入一个用户");
    }
}