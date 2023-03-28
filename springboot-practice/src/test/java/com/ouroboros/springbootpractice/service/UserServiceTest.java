package com.ouroboros.springbootpractice.service;

import com.ouroboros.springbootpractice.component.TransactionAOP;
import com.ouroboros.springbootpractice.component.TransactionProxyFactory;
import com.ouroboros.springbootpractice.service.impl.NonInterfaceUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    TransactionAOP transactionAOP;

    @Autowired
    NonInterfaceUser nonInterfaceUser;

    @Test
    void testForProxy() {
        UserService userServiceProxy = (UserService) TransactionProxyFactory.getProxyInstance(userService, transactionAOP);
        userServiceProxy.deleteUser();
    }

    @Test
    void testAOP() {
        userService.selectUser();
        System.out.println("===========");
        nonInterfaceUser.insertUser();
    }
}
