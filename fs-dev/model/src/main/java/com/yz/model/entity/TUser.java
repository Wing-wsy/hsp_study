package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 例子
 */
@Data
@TableName("t_user")
public class TUser extends BaseEntity implements Serializable {
    /**用户表主键ID*/
    @TableId
    private Long userId;

    /**昵称*/
    private String name;

    /**年龄*/
    private Integer age;


}