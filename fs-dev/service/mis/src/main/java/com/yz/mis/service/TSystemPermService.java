package com.yz.mis.service;

import com.yz.model.bo.mis.AddPermBO;
import com.yz.model.bo.mis.UpdatePermBO;

import java.util.ArrayList;
import java.util.HashMap;

public interface TSystemPermService {

    /**
     * 查询权限树
     */
    public ArrayList<HashMap> permTree(String language, Integer status);

    /**
     * 新增权限
     */
    public void addPerm(AddPermBO bo);

    /**
     * 删除权限
     */
    public void deletePerm(Long id);

    /**
     * 修改权限
     */
    public void updatePerm(UpdatePermBO bo);

}
