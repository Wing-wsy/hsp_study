package com.yz.mis.service;

import com.yz.model.bo.mis.AddMenuBO;
import com.yz.model.bo.mis.UpdateMenuBO;

import java.util.ArrayList;
import java.util.HashMap;


public interface TSystemMenuService {

    /**
     * 查询菜单树
     */
    public ArrayList<HashMap> menuTree(String language, Integer status);

    /**
     * 新增菜单
     */
    public void addMenu(AddMenuBO bo);

    /**
     * 删除菜单
     */
    public void deleteMenu(Long id);

    /**
     * 修改菜单
     */
    public void updateMenu(UpdateMenuBO bo);


}
