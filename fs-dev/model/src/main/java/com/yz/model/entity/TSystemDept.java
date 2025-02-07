package com.yz.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_system_dept")
public class TSystemDept extends BaseEntity implements Serializable {
    /**主键*/
    @TableId
    private Long id;

    /**部门名称*/
    private String deptName;

    /**部门编码*/
    private String deptCode;

    /**父级部门主键(预留字段，暂不使用)*/
    private Long parentId;

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

    /**
     * 是否删除
     */
    private Integer isDelete;
}