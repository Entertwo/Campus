package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import com.example.pojo.User;
import org.apache.catalina.connector.Response;


public interface IUserService extends IService<User> {

    Boolean select(User user);

    Boolean insertUser(User user);

    Boolean addScoreByUserId(String id, Integer value);

    IPage<User> getAllUsers(Integer currentPage, Integer pageSize);

    Boolean updateUser(User user);

    Boolean updateStatusById(Integer id, Boolean status);

    IPage<User> getAllUsers(Integer currentPage, Integer pageSize, User user);
}
