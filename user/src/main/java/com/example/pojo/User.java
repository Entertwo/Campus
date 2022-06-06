package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "tb_user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String userId;
    private String password;
    private String name;
    private Integer score;
    private Boolean status;
}