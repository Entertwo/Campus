package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "system_user")
public class Manager {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String managerId;
    private String password;
    private String name;
}