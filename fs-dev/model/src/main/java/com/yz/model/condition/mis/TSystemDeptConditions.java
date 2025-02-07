package com.yz.model.condition.mis;

import lombok.Data;

@Data
public class TSystemDeptConditions {

    private Long parentId;

    private String deptName;

    private String mobile;

    private String email;

    private String comment;

    private String deptCode;

    private String language;

    private int minSort;

    private int maxSort;

    private Integer status;

    private Integer sort;

    private Long id;

    public static TSystemDeptConditions newInstance() {
        return new TSystemDeptConditions();
    }

    public TSystemDeptConditions addParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public TSystemDeptConditions addDeptCode(String deptCode) {
        this.deptCode = deptCode;
        return this;
    }

    public TSystemDeptConditions addMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public TSystemDeptConditions addEmail(String email) {
        this.email = email;
        return this;
    }

    public TSystemDeptConditions addComment(String comment) {
        this.comment = comment;
        return this;
    }


    public TSystemDeptConditions addDeptName(String deptName) {
        this.deptName = deptName;
        return this;
    }


    public TSystemDeptConditions addLanguage(String language) {
        this.language = language;
        return this;
    }

    public TSystemDeptConditions addMinSort(int minSort) {
        this.minSort = minSort;
        return this;
    }

    public TSystemDeptConditions addMaxSort(int maxSort) {
        this.maxSort = maxSort;
        return this;
    }

    public TSystemDeptConditions addId(Long id) {
        this.id = id;
        return this;
    }

    public TSystemDeptConditions addStatus(Integer status) {
        this.status = status;
        return this;
    }

    public TSystemDeptConditions addSort(Integer sort) {
        this.sort = sort;
        return this;
    }


}
