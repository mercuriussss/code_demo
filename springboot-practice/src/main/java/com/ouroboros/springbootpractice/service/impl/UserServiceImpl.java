package com.ouroboros.springbootpractice.service.impl;

import com.ouroboros.springbootpractice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void insertUser() {
        System.out.println("插入一个用户");
    }
    @Override
    public void updateUser() {
        System.out.println("更新一个用户");
    }
    @Override
    public void deleteUser() {
        System.out.println("删除一个用户");
    }
    @Override
    public void selectUser() {
        System.out.println("选择一个用户");
    }
}
