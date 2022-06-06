package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.Manager;
import com.example.pojo.User;


public interface IManagerService extends IService<Manager> {


    Boolean select(User user);

    IPage<Manager> getAllUsers(Integer currentPage, Integer pageSize);

    Boolean updateManager(Manager manager);

    Boolean deleteById(Integer id);

    Boolean insertManager(Manager manager);

    IPage<Manager> getAllUsers(Integer currentPage, Integer pageSize, Manager manager);
}
