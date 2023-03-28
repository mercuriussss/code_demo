package com.ouroboros.springbootpractice.service;

public interface UserService {
    /**
     * 插入一个用户
     */
    public void insertUser();
    /**
     * 更新一个用户
     */
    public void updateUser();
    /**
     * 删除一个用户
     */
    public void deleteUser();
    /**
     * 选择一个用户
     */
    public void selectUser();
}