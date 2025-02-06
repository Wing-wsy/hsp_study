package com.yz.model.vo.mis;

import lombok.Data;

@Data
public class SelectRolePermTreeVO {
    /**主键*/
    private Integer id;

    /**权限*/
    private String permissionName;

    /**菜单名称*/
    private String menuCode;

    /**1有效，0禁用*/
    private Integer status;

    /**排序*/
    private Integer sort;

    /**是否拥有该权限*/
    private boolean have;

}