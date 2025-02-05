package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_system_permission")
public class TSystemPermission {
    /**主键*/
    @TableId
    private Integer id;

    /**权限*/
    private String permissionName;

    /**模块ID*/
    private Integer moduleId;

    /**行为ID*/
    private Integer actionId;

    /**菜单名称*/
    private String menuCode;

    /**1有效，0禁用*/
    private Integer status;

    /**排序*/
    private Integer sort;

    /**0有效，1删除*/
    private Integer isDelete;
}