package com.yz.model.condition.mis;

import lombok.Data;

@Data
public class TSystemRoleConditions {

    private String roleCode;

    private String language;

    private Integer status;

    private Integer sort;

    public static TSystemRoleConditions newInstance() {
        return new TSystemRoleConditions();
    }

    public TSystemRoleConditions addRoleCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    public TSystemRoleConditions addLanguage(String language) {
        this.language = language;
        return this;
    }

    public TSystemRoleConditions addStatus(Integer status) {
        this.status = status;
        return this;
    }

    public TSystemRoleConditions addSort(Integer sort) {
        this.sort = sort;
        return this;
    }

}
