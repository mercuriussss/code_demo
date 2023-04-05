package com.ouroboros.smallspring.test;

import com.ouroboros.smallspring.springframework.BeanDefinition;
import com.ouroboros.smallspring.springframework.BeanFactory;
import com.ouroboros.smallspring.test.bean.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiTest {

    @Test
    public void testBeanFactory() {
        // 1. 初始化BeanFactory
        BeanFactory beanFactory = new BeanFactory();

        // 2. 注册Bean
        String BeanName = "userService";
        BeanDefinition beanDefinition = new BeanDefinition(new UserService());
        beanFactory.registerBeanDefinition(BeanName, beanDefinition);

        // 3. 获取Bean
        UserService userService = (UserService) beanFactory.getBean(BeanName);
        userService.queryUserInfo();
    }
}
