package com.example.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.pojo.R;
import com.example.pojo.User;
import com.example.service.IManagerService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController

public class UserController {
    //    public User queryById(@PathVariable("id") Long id) {
    @Autowired
    private IUserService userService;
    @Autowired
    private IManagerService manageService;

    /**
     * 查询cookie
     * @param request a
     * @return  R
     */
    @GetMapping("/users/cookie")
    public R getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            R r = new R();
            for (Cookie cookie : cookies) {
                //3.获取cookie的名称
                String name = cookie.getName();
                //4.判断名称是否存在
                if ("name".equals(name)) {
                    //有该Cookie，不是第一次访问
                    r.setCookie(cookie.getValue());
                }
                if ("id".equals(name)) {
                    //有该Cookie，不是第一次访问
                    r.setId(Integer.valueOf(cookie.getValue()));
                }
            }
            return r;
        }
        return null;
    }

    /**
     * 根据用户id查用户
     * @param id 用户id
     * @return  用户
     */
    @GetMapping("/user/{id}")
    public User findById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return user;
    }

    /**
     * ManagerUser页面
     * @param currentPage 当前页
     * @param pageSize 每页显示数
     * @return user列表IPage的user
     */
/*    @GetMapping("/users/{currentPage}/{pageSize}")
    public IPage<User> getAllUsers(@PathVariable Integer currentPage,@PathVariable Integer pageSize){
        return userService.getAllUsers(currentPage,pageSize);
    }*/

    /**
     * ManagerUser页面
     * @param currentPage 当前页
     * @param pageSize 每页显示数
     * @return user列表IPage的user
     */
    @GetMapping("/users/{currentPage}/{pageSize}")
    public IPage<User> getAllUsersByPhone(@PathVariable Integer currentPage,@PathVariable Integer pageSize,User user){
         return userService.getAllUsers(currentPage,pageSize,user);
    }

    /**
     * 查询用户数
     * @return
     */
    @GetMapping("users/count")
    public Integer getUserCount(){
        return userService.count();
    }

    /**
     * 注册插入数据
     *添加用户
     * @param user 用户
     * @return  用R封装boolean
     */
    @PostMapping("/users")
    public R insertUser(@RequestBody User user) {
        R r = new R();
        user.setScore(0);
        Boolean flag = userService.insertUser(user);
        r.setFlag(flag);
        return r;
    }

    /**
     * @param user 用户信息
     * @return 登陆验证
     */
    @PostMapping("/users2")
    public R queryUser(@RequestBody User user, HttpServletResponse response) {
        R r = new R();
        Boolean flag = userService.select(user);
        r.setFlag(flag);
        if (flag) {
            //设置cookie
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("user_id",user.getUserId());
            queryWrapper.eq("password",user.getPassword());
            User one = userService.getOne(queryWrapper);
            setCookie(response, one.getName(),String.valueOf(one.getId()));
//            setCookie(response, String.valueOf(one.getId()));
//            System.out.println(one);
        }else{
            if(manageService.select(user)){
                r.setManager(true);
                r.setFlag(false);
            };
        }
        return r;
    }

    public void setCookie(HttpServletResponse response, String name,String id) {
        // 创建一个 cookie对象
        Cookie cookie = new Cookie("name", name);
        Cookie cookie2 = new Cookie("id",id);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie2.setMaxAge(7 * 24 * 60 * 60);
        //将cookie对象加入response响应
        response.addCookie(cookie);
        response.addCookie(cookie2);
    }

    /**
     * 用户确认收货后执行该操作
     * 根据传递的userId
     * 添加用户评分
     * @param id 用户id
     * @param value 分数
     * @return 真假
     */
    @PutMapping("/users/{id}/value/{value}")
    public Boolean addScore(@PathVariable String id,@PathVariable Integer value ) {
        Boolean flag=userService.addScoreByUserId(id,value);
        return flag;
    }

    /**
     * 更新用户数据
     * @param user 用户数据
     * @return  Boolean
     */
    @PutMapping("/users")
    public Boolean updateUser(@RequestBody User user ) {
        return userService.updateUser(user);
    }

    /**
     * 根据id更新用户状态
     * @param id 用户id
     * @param status 代送状态
     * @return Boolean
     */
    @PutMapping("/users/{id}/status/{status}")
    public Boolean updateStatusById(@PathVariable Integer id,@PathVariable Boolean status ) {
        return userService.updateStatusById(id,status);
    }

    /**
     * 删除cookie
     * @param request 请求cookie
     * @param response 把cookie的生命周期为
     */
    @DeleteMapping("/users/cookie")
    public void deleteCookie(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                //3.获取cookie的名称
                String name = cookie.getName();
                if ("name".equals(name)) {
                    //有该Cookie，不是第一次访问
                    Cookie cookie1 = new Cookie("name", null);
                    cookie1.setMaxAge(0);
                    cookie1.setPath("/");
                    response.addCookie(cookie1);
                }
                if ("id".equals(name)) {
                    //有该Cookie，不是第一次访问
                    Cookie cookie2 = new Cookie("id", null);
                    cookie2.setMaxAge(0);
                    cookie2.setPath("/");
                    response.addCookie(cookie2);
                }
            }
        }
    }

}
