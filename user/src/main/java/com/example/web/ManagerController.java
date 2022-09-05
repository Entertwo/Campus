package com.example.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.pojo.Manager;
import com.example.pojo.User;
import com.example.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 经理控制器
 *
 * @author tians
 * @date 2022/09/05
 */
@RestController
public class ManagerController {

    @Autowired
    private IManagerService managerService;

    /**
     * 得到所有经理
     * ManagerPeoper页面
     *
     * @param currentPage 当前页
     * @param pageSize    每页显示数
     * @param manager     经理
     * @return user列表IPage的user
     */
    @GetMapping("/managers/{currentPage}/{pageSize}")
    public IPage<Manager> getAllManagers(@PathVariable Integer currentPage, @PathVariable Integer pageSize,Manager manager){
        return managerService.getAllUsers(currentPage,pageSize,manager);
    }

    /**
     * 更新管理器
     * 更新管理员数据
     *
     * @param manager 管理员数据
     * @return Boolean
     */
    @PutMapping("/managers")
    public Boolean updateManager(@RequestBody Manager manager ) {
        return managerService.updateManager(manager);
    }

    /**
     * 插入经理
     * 新增管理员数据
     *
     * @param manager 管理员数据
     * @return Boolean
     */
    @PostMapping("/managers")
    public Boolean insertManager(@RequestBody Manager manager ) {
        return managerService.insertManager(manager);
    }

    /**
     * 删除通过id
     * manager删除
     *
     * @param id id
     * @return {@link Boolean}
     */
    @DeleteMapping("/managers/{id}")
    public  Boolean deleteById(@PathVariable Integer id) {
        return  managerService.deleteById(id);
    }
}
