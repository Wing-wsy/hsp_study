package com.yz.mis.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.common.constant.Basic;
import com.yz.common.constant.Strings;
import com.yz.common.util.JSONUtils;
import com.yz.common.util.ListUtils;
import com.yz.common.util.MapUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.mis.mapper.TSystemMenuMapper;
import com.yz.mis.service.TSystemMenuService;
import com.yz.model.bo.mis.AddMenuBO;
import com.yz.model.entity.TResponseErrorEnums;
import com.yz.model.entity.TSystemMenu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        ArrayList<HashMap> parentMenuList = systemMenuMapper.searchMenuTree(Basic.ONE_INT, language, null);
        for (HashMap hashMap : parentMenuList) {
            Long id = MapUtils.getLong(hashMap,"id");
            ArrayList<HashMap> sonMenuList = systemMenuMapper.searchMenuTree(Basic.TWO_INT, language,id);
            hashMap.put("sonMenuList",sonMenuList);
        }
        return parentMenuList;
    }

    // TODO 加入事务
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
            if (bo.getLevel().equals(Basic.ONE_STR)) {
                addParentMenu(language, menuName, bo.getMenuCode(), bo.getSort());
            }
            // 子级菜单
            if (bo.getLevel().equals(Basic.TWO_STR)) {
                // 根据 code和language 查询 id
                TSystemMenu tSystemMenu = selectTSystemMenu(null, bo.getFatherMenuCode(), language, 0).get(0);
                addSonMenu(tSystemMenu.getId(),language, menuName, bo.getMenuCode(), bo.getSort());
            }
        }
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
        } else {
            // 添加到中间，排序调整
            List<TSystemMenu> tSystemMenus = selectTSystemMenu(Basic.ZERO_LONG, null, language, sort);
            // 调整原有排序
            for (TSystemMenu tSystemMenu : tSystemMenus) {
                tSystemMenu.setSort(tSystemMenu.getSort() + 1);
                systemMenuMapper.updateById(tSystemMenu);
            }
        }
        insertMenu(menuName, Basic.ONE_INT, Basic.ZERO_LONG, menuCode, language, sort, Basic.ON);
    }

    private void addSonMenu(
            Long fatherId,
            String language,
            String menuName,
            String menuCode,
            int sort) {

        int maxSort = getSonMenuMaxSort(fatherId, language);
        if (sort >= maxSort) {
            // 添加到最后
            sort = maxSort + 1;
        } else {
            // 添加到中间，排序调整
            List<TSystemMenu> tSystemMenus = selectTSystemMenu(fatherId, null, language, sort);
            // 调整原有排序
            for (TSystemMenu tSystemMenu : tSystemMenus) {
                tSystemMenu.setSort(tSystemMenu.getSort() + 1);
                systemMenuMapper.updateById(tSystemMenu);
            }
        }
        insertMenu(menuName, Basic.TWO_INT, fatherId, menuCode, language, sort, Basic.ON);

    }

    // 获取顶级模块最大序号
    private int getParentMenuMaxSort(String language) {
        return systemMenuMapper.getMenuMaxSort(Basic.ONE_INT, language, null);
    }

    // 获取指定顶级模块下的子级模块最大序号
    private int getSonMenuMaxSort(Long fatherId,String language) {
        return systemMenuMapper.getMenuMaxSort(Basic.TWO_INT, language, fatherId);
    }

    private void insertMenu(String menuName, Integer level,
                            Long fatherId, String menuCode,
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
        tSystemMenu.setIsDelete(Basic.VAILD);
        systemMenuMapper.insert(tSystemMenu);
    }

    private List<TSystemMenu> selectTSystemMenu(Long fatherId, String menuCode,String language, int sort) {
        QueryWrapper<TSystemMenu> selectWrapper = new QueryWrapper<>();
        selectWrapper.eq("language", language);
        if (ObjectUtils.isNotNull(fatherId))
            selectWrapper.eq("father_id", fatherId);

        if (StrUtils.isNotBlank(menuCode))
            selectWrapper.eq("menu_code", menuCode);

        // 大于等于
        if (sort > 0)
            selectWrapper.ge("sort", sort);

        List<TSystemMenu> tSystemMenus = systemMenuMapper.selectList(selectWrapper);
        return tSystemMenus;
    }
}
