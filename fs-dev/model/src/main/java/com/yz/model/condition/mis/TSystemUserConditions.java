package com.yz.model.condition.mis;

import com.yz.model.bo.common.CommonPageBO;
import lombok.Data;

@Data
public class TSystemUserConditions extends CommonPageBO {

    // 状态 1有效，2休息 3离职 0禁用 4全部
    private Integer status;

    private String username;

    private String name;

    private String mobile;

    private String deptCode;

    private String roleCode;


    public static TSystemUserConditions newInstance() {
        return new TSystemUserConditions();
    }

    public TSystemUserConditions addStatus(Integer status) {
        this.status = status;
        return this;
    }

    public TSystemUserConditions addUsername(String username) {
        this.username = username;
        return this;
    }

    public TSystemUserConditions addName(String name) {
        this.name = name;
        return this;
    }

    public TSystemUserConditions addMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public TSystemUserConditions addDeptCode(String deptCode) {
        this.deptCode = deptCode;
        return this;
    }

    public TSystemUserConditions addRoleCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    public TSystemUserConditions addLanguage(String language) {
        super.setLanguage(language);
        return this;
    }

    public TSystemUserConditions addPage(Integer page) {
        super.setPage(page);
        return this;
    }

    public TSystemUserConditions addPageSize(Integer pageSize) {
        super.setPageSize(pageSize);
        return this;
    }


}
