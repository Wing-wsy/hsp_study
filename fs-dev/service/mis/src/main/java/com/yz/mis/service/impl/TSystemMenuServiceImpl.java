package com.yz.mis.service.impl;

import cn.hutool.core.map.MapUtil;
import com.yz.common.constant.Basic;
import com.yz.common.util.JSONUtils;
import com.yz.common.util.MapUtils;
import com.yz.mis.mapper.TSystemMenuMapper;
import com.yz.mis.service.TSystemMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 测试
 */
@Service
public class TSystemMenuServiceImpl implements TSystemMenuService {

    @Resource
    private TSystemMenuMapper systemMenuMapper;

    @Override
    public ArrayList<HashMap> menuTree(String language) {
        ArrayList<HashMap> parentMenuList = systemMenuMapper.searchMenuTree(Basic.one, language, null);
        for (HashMap hashMap : parentMenuList) {
            Long id = MapUtils.getLong(hashMap,"id");
            ArrayList<HashMap> sonMenuList = systemMenuMapper.searchMenuTree(Basic.two, language,id);
            hashMap.put("sonMenuList",sonMenuList);
        }
        return parentMenuList;
    }
}
