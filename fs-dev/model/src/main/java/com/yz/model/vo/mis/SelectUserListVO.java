package com.yz.model.vo.mis;

import lombok.Data;

@Data
public class SelectUserListVO {

    /**主键*/
    private Long id;

    /**用户名*/
    private String username;

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
    private Integer root;

    /**部门名称*/
    private String deptName;

    /**1有效 2休息 3离职 0禁用*/
    private Integer status;


}