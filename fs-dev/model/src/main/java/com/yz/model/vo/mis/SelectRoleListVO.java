package com.yz.model.vo.mis;

import lombok.Data;

@Data
public class SelectRoleListVO {
    /**主键*/
    private Integer id;

    /**角色名称*/
    private String roleName;

    /**角色编码*/
    private String roleCode;

    /**描述*/
    private String comment;

    /**1有效，0禁用*/
    private Integer status;

    /**排序*/
    private Integer sort;

}