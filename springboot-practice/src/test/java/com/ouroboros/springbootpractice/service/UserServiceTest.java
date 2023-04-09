package com.ouroboros.springbootpractice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ouroboros.springbootpractice.component.TransactionAOP;
import com.ouroboros.springbootpractice.component.TransactionProxyFactory;
import com.ouroboros.springbootpractice.dao.original.UserTableDAO;
import com.ouroboros.springbootpractice.dao.sharding.UserTableShardingDAO;
import com.ouroboros.springbootpractice.pojo.UserTableDO;
import com.ouroboros.springbootpractice.service.impl.NonInterfaceUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    TransactionAOP transactionAOP;

    @Autowired
    NonInterfaceUser nonInterfaceUser;

    @Autowired
    UserTableDAO userTableDAO;

    @Autowired
    UserTableShardingDAO userTableShardingDAO;

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

    @Test
    void testDAO() throws JsonProcessingException {
        List<UserTableDO> list = userTableDAO.selectAll();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(list));
    }

    @Test
    void testShardingDAO() throws JsonProcessingException {
        List<UserTableDO> list = userTableShardingDAO.selectByUserId("user_table2222");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(list));
    }
}
