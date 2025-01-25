package com.yz.mis.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.common.constant.Basic;
import com.yz.common.constant.Strings;
import com.yz.common.util.JSONUtils;
import com.yz.common.util.ListUtils;
import com.yz.common.util.MapUtils;
import com.yz.mis.mapper.TSystemMenuMapper;
import com.yz.mis.service.TSystemMenuService;
import com.yz.model.bo.mis.AddMenuBO;
import com.yz.model.entity.TResponseErrorEnums;
import com.yz.model.entity.TSystemMenu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    // TODO 将来这里需要添加事务
    @Override
    public void addMenu(AddMenuBO bo) {
        List<String> languages = ListUtils.getLanguageList();
        for (String language : languages) {
            String menuName = null;
            if (Strings.LOCALE_ES_LOWER.equals(language)) {
                menuName = bo.getMenuNameByES();
            } else {
                menuName = bo.getMenuNameByZH();
            }
            // 顶级菜单
            if (bo.getLevel().equals(Basic.one_str)) {
                addParentMenu(language, menuName, bo.getMenuCode(), bo.getSort());
            }
            // 子级菜单
            if (bo.getLevel().equals(Basic.two_str)) {

            }
        }



        System.out.println(bo);
    }

    private void addParentMenu(
            String language,
            String menuName,
            String menuCode,
            int sort) {

        int maxSort = getParentMenuMaxSort(language);
        if (sort >= maxSort) {
            // 添加到最后
            sort = maxSort + 1;
            insertMenu(menuName, Basic.one, Basic.zero, menuCode, language, sort, Basic.ON);
        } else {
            // 添加到中间，排序调整
            QueryWrapper<TSystemMenu> selectWrapper = new QueryWrapper<>();
            selectWrapper.eq("fatherId", Basic.zero);
            selectWrapper.eq("language", language);
            selectWrapper.ge("sort", sort);
            List<TSystemMenu> tSystemMenus = systemMenuMapper.selectList(selectWrapper);
            for (TSystemMenu tSystemMenu : tSystemMenus) {
                System.out.println(tSystemMenu);
            }
            
        }

    }

    private void addSonMenu() {

    }

    // 获取顶级模块最大序号
    private int getParentMenuMaxSort(String language) {
        return systemMenuMapper.getMenuMaxSort(Basic.one, language, null);
    }

    // 获取指定顶级模块下的子级模块最大序号
    private int getSonMenuMaxSort(String language, Long fatherId) {
        return systemMenuMapper.getMenuMaxSort(Basic.two, language, fatherId);
    }

    private void insertMenu(String menuName, Integer level,
                            Integer fatherId, String menuCode,
                            String language,Integer sort,
                            Integer status) {
        TSystemMenu tSystemMenu = new TSystemMenu();
        tSystemMenu.setMenuName(menuName);
        tSystemMenu.setLevel(level);
        tSystemMenu.setFatherId(fatherId);
        tSystemMenu.setMenuCode(menuCode);
        tSystemMenu.setLanguage(language);
        tSystemMenu.setSort(sort);
        tSystemMenu.setStatus(status);
        systemMenuMapper.insert(tSystemMenu);
    }
}
