package com.yz.model.condition.mis;

import com.yz.model.bo.common.CommonPageBO;
import lombok.Data;

@Data
public class TSystemRoleConditions extends CommonPageBO {

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

    public TSystemRoleConditions addPage(Integer page) {
        super.setPage(page);
        return this;
    }

    public TSystemRoleConditions addPageSize(Integer pageSize) {
        super.setPageSize(pageSize);
        return this;
    }

}
