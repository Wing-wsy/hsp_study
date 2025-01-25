package com.yz.mis.service;

import com.yz.model.bo.mis.AddMenuBO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 测试
 */
public interface TSystemMenuService {

    public ArrayList<HashMap> menuTree(String language);

    public void addMenu(AddMenuBO bo);


}
