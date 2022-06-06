package com.example;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pojo.Manager;
import com.example.pojo.User;
import com.example.service.IManagerService;
import com.example.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserApplicationTests {

    @Autowired
    private IUserService userService;

    @Autowired
    private IManagerService manageService;

    @Test
    void testUserMapper() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", "17640966258");
        queryWrapper.eq("password", "123546");
        System.out.println(userService.getOne(queryWrapper));
    }

    @Test
    void testUser() {
        List<User> list = userService.list();
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    void testManage() {
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("manager_id", "17600966258")
                .eq("password", "1234");
        Manager one = manageService.getOne(queryWrapper);
    }

    @Test
    void testStatus() {
        User user = new User();
        user.setStatus(true);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", 17);
        System.out.println(userService.update(user, updateWrapper));
    }
//    @Test
//    void testUserMapper2() {
//        User user =new User();
//        user.setUserId("17640966258");
//        user.setPassword("12546");
//        System.out.println(userService.query(user));
//    }String path = xxx.class.getClassLoader().getResource("targetFile.txt").getPath();
}
