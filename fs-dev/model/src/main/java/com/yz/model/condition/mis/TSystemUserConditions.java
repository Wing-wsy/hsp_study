package com.yz.model.condition.mis;

import com.yz.model.bo.common.CommonPageBO;
import lombok.Data;

@Data
public class TSystemUserConditions extends CommonPageBO {

    // 状态 1有效，2休息 3离职 0禁用 4全部
    private Integer status;

    private String username;

    // 精确匹配
    private String usernameByExact;

    private String password;

    private String name;

    private String mobile;

    private String email;

    private String deptCode;

    private String roleCode;

    private Integer sex;

    private Integer root;


    public static TSystemUserConditions newInstance() {
        return new TSystemUserConditions();
    }

    public TSystemUserConditions addStatus(Integer status) {
        this.status = status;
        return this;
    }

    public TSystemUserConditions addSex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public TSystemUserConditions addRoot(Integer root) {
        this.root = root;
        return this;
    }

    public TSystemUserConditions addUsername(String username) {
        this.username = username;
        return this;
    }


    public TSystemUserConditions addUsernameByExact(String usernameByExact) {
        this.usernameByExact = usernameByExact;
        return this;
    }

    public TSystemUserConditions addPassword(String password) {
        this.password = password;
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

    public TSystemUserConditions addEmail(String email) {
        this.email = email;
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
