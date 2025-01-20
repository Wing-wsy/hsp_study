package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_system_user")
public class SystemUser {
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

    /**创建日期*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;

    /**0有效，1删除*/
    private Byte isDelete;

}