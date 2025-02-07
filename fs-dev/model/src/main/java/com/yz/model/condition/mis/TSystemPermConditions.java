package com.yz.model.condition.mis;

import lombok.Data;

@Data
public class TSystemPermConditions {

    private String permissionName;

    private String menuCode;

    private Integer status;

    private Integer sort;

    public static TSystemPermConditions newInstance() {
        return new TSystemPermConditions();
    }

    public TSystemPermConditions addPermissionName(String permissionName) {
        this.permissionName = permissionName;
        return this;
    }

    public TSystemPermConditions addMenuCode(String menuCode) {
        this.menuCode = menuCode;
        return this;
    }

    public TSystemPermConditions addStatus(Integer status) {
        this.status = status;
        return this;
    }

    public TSystemPermConditions addSort(Integer sort) {
        this.sort = sort;
        return this;
    }

}
