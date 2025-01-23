package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_system_menu")
public class TSystemMenu {
    /**主键*/
    @TableId
    private Integer id;

    /**菜单名称*/
    private String menuName;

    /**菜单级别 顶级菜单值为1*/
    private Integer level;

    /**父级ID 顶级菜单值为0*/
    private Integer fatherId;

    /**菜单编码*/
    private String menuCode;

    /**语言编码*/
    private String language;

    /**排序*/
    private Integer sort;

    /**1有效，0禁用*/
    private Byte status;

    /**0有效，1删除*/
    private Byte isDelete;
}