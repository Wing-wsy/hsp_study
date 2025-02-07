package com.yz.model.condition.mis;

import lombok.Data;

@Data
public class TSystemDeptConditions {

    private Long parentId;

    private String deptCode;

    private String language;

    private int minSort;

    private int maxSort;

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


}
