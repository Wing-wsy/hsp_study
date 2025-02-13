package com.yz.mis.service;

import com.yz.common.util.page.PageResult;
import com.yz.model.bo.mis.InsertRoleBO;
import com.yz.model.bo.mis.UpdateRoleBO;
import com.yz.model.condition.mis.TSystemRoleConditions;
import com.yz.model.vo.mis.SelectRoleListVO;

import java.util.ArrayList;
import java.util.HashMap;


public interface TSystemRoleService {

    /**
     * 查询角色
     */
    public PageResult<SelectRoleListVO> selectRoleList(TSystemRoleConditions conditions);

    /**
     * 查询角色权限树
     */
    public ArrayList<HashMap> selectRolePermTree(String roleCode, Integer status, String language);

    /**
     * 新增角色
     */
    public void insertRole(InsertRoleBO bo);

    /**
     * 修改角色
     */
    public void updateRole(UpdateRoleBO bo);


}
