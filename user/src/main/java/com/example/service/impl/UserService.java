package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.IUserService;
import org.apache.catalina.connector.Response;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService{

    /**
     * 查询用户，并登陆
     * @param user
     * @return
     */
    @Override
    public Boolean select(User user) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getUserId());
        queryWrapper.eq("password",user.getPassword());
        User one = this.getOne(queryWrapper);
        //成功登陆，设置session
        return one != null;

    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public Boolean insertUser(User user) {
        boolean save = false;
        try {
            //添加用户状态
            user.setStatus(false);
            save = this.save(user);
            return save;
        } catch (Exception e) {
            return save;
        }
    }

    /**
     * 根据id查user分数在和score相加求平均
     * 修改user分数
     * @param id  用户id
     * @param value 分数
     * @return  添加是否成功
     */
    @Override
    public Boolean addScoreByUserId(String id, Integer value) {
        User user = this.getById(id);
        UpdateWrapper<User> updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("id",id)
                .set("score",(user.getScore()+value)/2);
        return this.update(updateWrapper);
    }

    /**
     * 根据当前页和，每页显示数
     * @param currentPage 当前页
     * @param pageSize 每页显示数
     * @return Users
     */
    @Override
    public IPage<User> getAllUsers(Integer currentPage, Integer pageSize) {
        IPage<User> page=new Page<>(currentPage,pageSize);
        return this.page(page, null);
    }

    /**
     * 根据当前页和，每页显示数，返回用户
     * @param currentPage 当前页
     * @param pageSize 每页显示条数
     * @param user phone
     * @return
     */
    @Override
    public IPage<User> getAllUsers(Integer currentPage, Integer pageSize, User user) {
        IPage<User> page=new Page<>(currentPage,pageSize);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.likeRight(Strings.isNotEmpty(user.getUserId()),"user_id",user.getUserId());
        return this.page(page, queryWrapper);
    }
    /**
     * 更新用户数据
     * @param user 用户
     * @return Boolean
     */
    @Override
    public Boolean updateUser(User user) {
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",user.getId());
        return this.update(user,updateWrapper);
    }

    /**
     * 根据id更新用户状态
     * @param id 用户id
     * @param status 用户代送状态
     * @return Boolean
     */
    @Override
    public Boolean updateStatusById(Integer id, Boolean status) {
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("status",status);
        return this.update(updateWrapper);
    }


}
