package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userId;
    private String password;
    private String name;
    private Integer score;
    private Boolean status;
}