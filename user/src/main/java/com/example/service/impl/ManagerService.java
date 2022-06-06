package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.ManageMapper;
import com.example.pojo.Manager;
import com.example.pojo.User;
import com.example.service.IManagerService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

@Service
public class ManagerService extends ServiceImpl<ManageMapper, Manager> implements IManagerService {

    /**
     * UserService的调用，如果要修改用远端调用，Manager但弄一个模块
     * @param user 账户密码
     * @return Boolean
     */
    @Override
    public Boolean select(User user) {
        QueryWrapper<Manager> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("manager_id",user.getUserId())
                .eq("password",user.getPassword());
        Manager one = this.getOne(queryWrapper);
        return one != null;
    }

    /**
     * 查询所有管理员
     * @param currentPage 当前页
     * @param pageSize 每页显示的条数
     * @return Ipage数据
     */
    @Override
    public IPage<Manager> getAllUsers(Integer currentPage, Integer pageSize) {
        IPage<Manager> page=new Page<>(currentPage,pageSize);
        return this.page(page, null);
    }

    @Override
    public IPage<Manager> getAllUsers(Integer currentPage, Integer pageSize, Manager manager) {
        IPage<Manager> page=new Page<>(currentPage,pageSize);
        QueryWrapper<Manager> queryWrapper=new QueryWrapper<>();
        queryWrapper.likeRight(Strings.isNotEmpty(manager.getManagerId()),"manager_id",manager.getManagerId());
        return this.page(page, queryWrapper);
    }
    /**
     * 更新管理员数据
     * @param manager 用户
     * @return Boolean
     */
    @Override
    public Boolean updateManager(Manager manager) {
        UpdateWrapper<Manager> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",manager.getId());
        return this.update(manager,updateWrapper);
    }

    /**
     * 删除管理员
     * @param id 管理员id
     * @return Boolean
     */
    @Override
    public Boolean deleteById(Integer id) {
        return this.removeById(id);
    }

    /**
     * 新增管理员
     * @param manager 管理员
     * @return boolean
     */
    @Override
    public Boolean insertManager(Manager manager) {
        return this.save(manager);
    }


}
