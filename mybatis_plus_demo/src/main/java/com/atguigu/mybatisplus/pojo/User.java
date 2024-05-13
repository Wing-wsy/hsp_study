package com.atguigu.mybatisplus.pojo;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author wing
 * @create 2024/5/12
 */
@Data
// 设置实体类对应的表名，如果表名是t_user，那么这里要改成@TableName("t_user")
@TableName("user")
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @TableLogic // 逻辑删除标识（会影响全部SQL都自动加上这个标识）
    private Integer isDeleted;
    private SexEnum sex;
}
