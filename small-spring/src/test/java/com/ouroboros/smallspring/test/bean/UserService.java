package com.ouroboros.smallspring.test.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
public class UserService {

    public void queryUserInfo() {
        log.info("查询用户信息");
    }
}
