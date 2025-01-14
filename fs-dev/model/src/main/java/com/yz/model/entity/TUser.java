package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 例子
 */
@Data
@TableName("t_user")
public class TUser implements Serializable {
    /**用户表主键ID*/
    @TableId
    private Long userId;

    /**昵称*/
    private String name;

    /**年龄*/
    private Integer age;

    /**创建日期*/
    private LocalDateTime createTime;

    /**更新日期*/
    private LocalDateTime updateTime;

    /**是否删除*/
    private int isDelete;

}