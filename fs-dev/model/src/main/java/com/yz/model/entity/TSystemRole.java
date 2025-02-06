package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_system_role")
public class TSystemRole {
    /**主键*/
    @TableId
    private Integer id;

    /**角色名称*/
    private String roleName;

    /**角色编码*/
    private String roleCode;

    /**权限集合*/
    private String permissions;

    /**语言编码*/
    private String language;

    /**描述*/
    private String comment;

    /**系统角色内置权限*/
    private String defaultPermissions;

    /**是否为系统内置角色*/
    private Boolean systemic;

    /**1有效，0禁用*/
    private Integer status;

    /**排序*/
    private Integer sort;

}