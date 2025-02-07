package com.yz.model.vo.mis;

import lombok.Data;

@Data
public class SelectDeptListVO {

    /**主键*/
    private Long id;

    /**部门名称*/
    private String deptName;

    /**部门编码*/
    private String deptCode;

    /**部门电话*/
    private String mobile;

    /**部门邮箱*/
    private String email;

    /**描述*/
    private String comment;

    /**排序*/
    private Integer sort;

    /**1有效，2禁用*/
    private Integer status;

    /**语言编码*/
    private String language;

}