package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName(value = "shop")
public class Shop {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String address;
    private String shopDesc;
    private Double price;
    private String pic;
    private Integer userId;
    private Integer sendId;
    private String startTime;
    private String endTime;
    private int tradeFiled;
    @TableField(exist = false)
    private User user;
}