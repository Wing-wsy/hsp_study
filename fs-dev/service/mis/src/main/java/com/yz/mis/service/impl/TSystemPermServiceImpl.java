package com.yz.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.common.constant.Basic;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.mis.mapper.TSystemPermissionMapper;
import com.yz.mis.service.TSystemMenuService;
import com.yz.mis.service.TSystemPermService;
import com.yz.model.entity.TSystemMenu;
import com.yz.model.entity.TSystemPermission;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class TSystemPermServiceImpl implements TSystemPermService {

    @Resource
    private TSystemMenuService tSystemMenuService;

    @Resource
    private TSystemPermissionMapper tSystemPermissionMapper;


    @Override
    public ArrayList<HashMap> permTree(String language, Integer status) {
        ArrayList<HashMap> hashMaps = tSystemMenuService.menuTree(language, status);
        for (HashMap hashMap : hashMaps) {
            // 二级菜单
            ArrayList<HashMap> sonHashMap = (ArrayList)hashMap.get("sonMenuList");
            for (HashMap map : sonHashMap) {
                String menuCode = (String)map.get("menuCode");

                List<TSystemPermission> tSystemPermissions = selectTSystemPermission(menuCode, status);
                map.put("permList", tSystemPermissions);

                System.out.println(map);
            }
            System.out.println(hashMap);
        }
        return hashMaps;
    }


    private List<TSystemPermission> selectTSystemPermission(String menuCode, Integer status) {
        QueryWrapper<TSystemPermission> selectWrapper = new QueryWrapper<>();
        selectWrapper.eq("is_delete", Basic.VAILD);

        if (StrUtils.isNotBlank(menuCode))
            selectWrapper.eq("menu_code", menuCode);

        if (ObjectUtils.isNotNull(status) && status == Basic.NORMAL)
            selectWrapper.eq("status", status);
        
        selectWrapper.orderByAsc("sort");
        List<TSystemPermission> tSystemPermissions = tSystemPermissionMapper.selectList(selectWrapper);
        return tSystemPermissions;
    }



}
