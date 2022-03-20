package org.imooc.mall.service.Impl;

import org.imooc.mall.pojo.User;
import org.imooc.mall.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Test
    public void register() {
        User user = new User();
        user.setEmail("2257714950@qq.com");
        //malluser.AMDIN.getCode()
        user.setRole(0);
        Date date = new Date();
        user.setCreatimeTime(date);
        user.setUpdateTime(date);
        user.setUsername("何必词");
        user.setPassword("123456");
        userService.register(user);
    }
}