package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "rim")
public class Rim {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String rimTextH5;
    private String rimTextH3;
    private String rimP;

}