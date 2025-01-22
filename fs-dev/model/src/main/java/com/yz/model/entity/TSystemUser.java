package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_system_user")
public class TSystemUser extends BaseEntity implements Serializable {
    /**主键*/
    @TableId
    private Long id;

    /**用户名*/
    private String username;

    /**密码（加密）*/
    private String password;

    /**姓名*/
    private String name;

    /**性别 1男 2女 0未知*/
    private Integer sex;

    /**电话*/
    private String mobile;

    /**邮箱*/
    private String email;

    /**角色*/
    private String role;

    /**是否为超级管理员*/
    private Byte root;

    /**部门编号*/
    private Integer deptId;

    /**1有效，2离职，3禁用*/
    private Byte status;

}