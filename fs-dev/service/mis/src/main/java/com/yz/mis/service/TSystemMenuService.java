package com.yz.mis.service;

import com.yz.model.bo.mis.AddMenuBO;
import com.yz.model.bo.mis.DeleteMenuBO;
import com.yz.model.bo.mis.UpdateMenuBO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 测试
 */
public interface TSystemMenuService {

    public ArrayList<HashMap> menuTree(String language, Integer status);

    public void addMenu(AddMenuBO bo);

    public void deleteMenu(Long id);

    public void updateMenu(UpdateMenuBO bo);


}
