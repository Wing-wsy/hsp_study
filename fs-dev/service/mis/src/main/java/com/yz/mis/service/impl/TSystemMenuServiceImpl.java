package com.yz.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.common.constant.Basic;
import com.yz.common.constant.Strings;
import com.yz.common.exception.GraceException;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.CollUtils;
import com.yz.common.util.ListUtils;
import com.yz.common.util.MapUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.mis.mapper.TSystemMenuMapper;
import com.yz.mis.service.TSystemMenuService;
import com.yz.model.bo.mis.AddMenuBO;
import com.yz.model.bo.mis.UpdateMenuBO;
import com.yz.model.condition.mis.TSystemMenuConditions;
import com.yz.model.entity.TSystemMenu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class TSystemMenuServiceImpl implements TSystemMenuService {

    @Resource
    private TSystemMenuMapper tSystemMenuMapper;

    @Override
    public ArrayList<HashMap> menuTree(String language, Integer status) {
        ArrayList<HashMap> parentMenuList = tSystemMenuMapper.searchMenuTree(Basic.ONE_INT, language, null, status);
        for (HashMap hashMap : parentMenuList) {
            Long id = MapUtils.getLong(hashMap,"id");
            ArrayList<HashMap> sonMenuList = tSystemMenuMapper.searchMenuTree(Basic.TWO_INT, language, id, status);
            hashMap.put("sonMenuList",sonMenuList);
        }
        return parentMenuList;
    }

    // TODO 加入事务
    @Override
    public void addMenu(AddMenuBO bo) {
        // 存在则不能重复添加
        boolean existRecords = isExistRecords(bo.getMenuCode());
        if (existRecords) {
            GraceException.display(ResponseStatusEnum.MENU_INSERT_ERROR);
        }

        List<String> languages = ListUtils.getLanguageList();
        for (String language : languages) {
            String menuName = null;
            if (Strings.LOCALE_ES.equals(language)) {
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
                TSystemMenuConditions conditions = TSystemMenuConditions.newInstance()
                        .addMenuCode(bo.getFatherMenuCode())
                        .addLanguage(language);
                TSystemMenu tSystemMenu = selectTSystemMenu(conditions).get(0);
                addSonMenu(tSystemMenu.getId(),language, menuName, bo.getMenuCode(), bo.getSort());
            }
        }
    }

    @Override
    public void deleteMenu(Long id) {
        TSystemMenu tSystemMenu = tSystemMenuMapper.selectById(id);

        // 1. 删除顶级菜单
        if (tSystemMenu.getLevel() == 1) {
            List<String> languages = ListUtils.getLanguageList();
            for (String language : languages) {
                // 根据 code和language 查询 id
                TSystemMenuConditions conditions1 = TSystemMenuConditions.newInstance()
                        .addMenuCode(tSystemMenu.getMenuCode())
                        .addLanguage(language);
                TSystemMenu tSystemMenu1 = selectTSystemMenu(conditions1).get(0);

                // 1.1 如果还存在子级菜单，则不能删除
                TSystemMenuConditions conditions2 = TSystemMenuConditions.newInstance()
                        .addFatherId(tSystemMenu1.getId());

                List<TSystemMenu> tSystemMenus = selectTSystemMenu(conditions2);
                if (CollUtils.isNotEmpty(tSystemMenus)) {
                    GraceException.display(ResponseStatusEnum.MENU_DELETE_ERROR);
                }

                // 1.2 修改其他顶级菜单排序
                TSystemMenuConditions conditions3 = TSystemMenuConditions.newInstance()
                        .addFatherId(Basic.ZERO_LONG)
                        .addLanguage(language)
                        .addMinSort(tSystemMenu1.getSort() + 1);
                List<TSystemMenu> tSystemMenus1 = selectTSystemMenu(conditions3);

                for (TSystemMenu systemMenu : tSystemMenus1) {
                    systemMenu.setSort(systemMenu.getSort() - 1);
                    tSystemMenuMapper.updateById(systemMenu);
                }

                // 1.3 删除顶级菜单
                tSystemMenu1.setIsDelete(Basic.DELETE);
                tSystemMenuMapper.updateById(tSystemMenu1);
            }
        }

        // 2. 删除子级菜单
        if (tSystemMenu.getLevel() == 2) {
            List<String> languages = ListUtils.getLanguageList();
            for (String language : languages) {
                // 根据 code和language 查询 id
                TSystemMenuConditions conditions1 = TSystemMenuConditions.newInstance()
                        .addMenuCode(tSystemMenu.getMenuCode())
                        .addLanguage(language);
                TSystemMenu tSystemMenu1 = selectTSystemMenu(conditions1).get(0);

                TSystemMenuConditions conditions2 = TSystemMenuConditions.newInstance()
                        .addFatherId(tSystemMenu1.getFatherId())
                        .addLanguage(language)
                        .addMinSort(tSystemMenu.getSort() + 1);
                List<TSystemMenu> tSystemMenus = selectTSystemMenu(conditions2);

                // 2.1 调整其他子级菜单排序
                for (TSystemMenu systemMenu : tSystemMenus) {
                    systemMenu.setSort(systemMenu.getSort() - 1);
                    tSystemMenuMapper.updateById(systemMenu);
                }

                // 2.2 删除子级菜单
                tSystemMenu1.setIsDelete(Basic.DELETE);
                tSystemMenuMapper.updateById(tSystemMenu1);
            }
        }
    }

    @Override
    public void updateMenu(UpdateMenuBO bo) {
        // 修改后的值不能重复
        if (StrUtils.isNotBlank(bo.getMenuCode())) {
            boolean existRecords = isExistRecords(bo.getMenuCode());
            if (existRecords) {
                GraceException.display(ResponseStatusEnum.MENU_UPDATE_ERROR);
            }
        }


        TSystemMenu tSystemMenu = tSystemMenuMapper.selectById(bo.getId());
        List<String> languages = ListUtils.getLanguageList();
        for (String language : languages) {
            TSystemMenuConditions conditions1 = TSystemMenuConditions.newInstance()
                    .addMenuCode(tSystemMenu.getMenuCode())
                    .addLanguage(language);
            TSystemMenu tSystemMenu1 = selectTSystemMenu(conditions1).get(0);
            String menuName = null;
            if (Strings.LOCALE_ES.equals(language)) {
                menuName = bo.getMenuNameByES();
            } else {
                menuName = bo.getMenuNameByZH();
            }

            // 上移
            if (StrUtils.isNotBlank(bo.getMoveMode()) && Basic.UP.equals(bo.getMoveMode())) {
                if (tSystemMenu1.getSort() == Basic.ONE_INT)
                    GraceException.display(ResponseStatusEnum.MENU_MOVE_UP_ERROR);

                // 找到上一个菜单,并将序号+1
                TSystemMenuConditions conditions2 = TSystemMenuConditions.newInstance()
                        .addFatherId(tSystemMenu1.getFatherId())
                        .addLanguage(language)
                        .addMinSort(tSystemMenu1.getSort() - 1);
                TSystemMenu tSystemMenu2 = selectTSystemMenu(conditions2).get(0);
                tSystemMenu2.setSort(tSystemMenu2.getSort() + 1);
                tSystemMenuMapper.updateById(tSystemMenu2);

                // 将当前菜单序号-1
                tSystemMenu1.setSort(tSystemMenu1.getSort() - 1);
                tSystemMenuMapper.updateById(tSystemMenu1);
            }

            // 下移
            if (StrUtils.isNotBlank(bo.getMoveMode()) && Basic.DOWN.equals(bo.getMoveMode())) {
                int maxSort = getMenuMaxSort(tSystemMenu1.getFatherId(), language, tSystemMenu1.getLevel());
                if (tSystemMenu1.getSort() == maxSort)
                    GraceException.display(ResponseStatusEnum.MENU_MOVE_DOWN_ERROR);

                // 找到下一个菜单,并将序号-1
                TSystemMenuConditions conditions3 = TSystemMenuConditions.newInstance()
                        .addFatherId(tSystemMenu1.getFatherId())
                        .addLanguage(language)
                        .addMinSort(tSystemMenu1.getSort() + 1);
                TSystemMenu tSystemMenu2 = selectTSystemMenu(conditions3).get(0);
                tSystemMenu2.setSort(tSystemMenu2.getSort() - 1);
                tSystemMenuMapper.updateById(tSystemMenu2);

                // 将当前菜单序号+1
                tSystemMenu1.setSort(tSystemMenu1.getSort() + 1);
                tSystemMenuMapper.updateById(tSystemMenu1);
            }

            if (StrUtils.isBlank(bo.getMoveMode())) {
                // 1. 判断是否需要修改排序（该方式暂不使用，使用移动排序的方式）
                if (bo.getSort() > 0 && bo.getSort() != tSystemMenu1.getSort()) {
                    int minSort = 0;
                    int maxSort = 0;
                    int moveSort = 0;

                    // 1.1 排序前移
                    if (bo.getSort() < tSystemMenu1.getSort()) {
                        minSort = bo.getSort();
                        maxSort = tSystemMenu1.getSort();
                        moveSort = 1;
                    } else {
                        // 1.2 排序前移
                        minSort = tSystemMenu1.getSort() + 1;
                        maxSort = bo.getSort() + 1;
                        moveSort = -1;
                    }

                    // 1.3 查询需要调整排序的记录，并更新
                    TSystemMenuConditions conditions4 = TSystemMenuConditions.newInstance()
                            .addFatherId(tSystemMenu1.getFatherId())
                            .addLanguage(language)
                            .addMinSort(minSort)
                            .addMaxSort(maxSort);
                    List<TSystemMenu> tSystemMenus = selectTSystemMenu(conditions4);
                    for (TSystemMenu systemMenu : tSystemMenus) {
                        systemMenu.setSort(systemMenu.getSort() + moveSort);
                        tSystemMenuMapper.updateById(systemMenu);
                    }
                    tSystemMenu1.setSort(bo.getSort());
                    tSystemMenuMapper.updateById(tSystemMenu1);
                } else {
                    // 2. 不需要修改排序，继续判断是否是修改禁用状态
                    if (ObjectUtils.isNotNull(bo.getStatus()) && bo.getStatus() != tSystemMenu1.getStatus()) {
                        int minSort = 0;
                        int moveSort = 0;
                        int status = 0;
                        Long id = null;

                        // 2.1 禁用状态修改，需要调整排序
                        if (Basic.ON == bo.getStatus()) {
                            // 开启
                            minSort = tSystemMenu1.getSort();
                            moveSort = 1;
                            status = Basic.ON;
                            id = tSystemMenu1.getId();
                        }
                        if (Basic.OFF == bo.getStatus()) {
                            // 禁用
                            minSort = tSystemMenu1.getSort() + 1;
                            moveSort = -1;
                            status = Basic.OFF;
                        }
                        // 2.2 查询需要调整的记录，并进行调整
                        TSystemMenuConditions conditions5 = TSystemMenuConditions.newInstance()
                                .addFatherId(tSystemMenu1.getFatherId())
                                .addLanguage(language)
                                .addMinSort(minSort)
                                .addId(id);
                        List<TSystemMenu> tSystemMenus = selectTSystemMenu(conditions5);
                        for (TSystemMenu systemMenu : tSystemMenus) {
                            systemMenu.setSort(systemMenu.getSort() + moveSort);
                            tSystemMenuMapper.updateById(systemMenu);
                        }
                        tSystemMenu1.setStatus(status);
                        tSystemMenuMapper.updateById(tSystemMenu1);
                    } else {
                        // 3. 不需要修改排序，也不需要修改状态（最简单的修改）
                        updateMenu(tSystemMenu1, menuName, bo.getMenuCode(),null, null);
                    }
                }
            }
        }
    }

    private void addParentMenu(
            String language,
            String menuName,
            String menuCode,
            Integer sort) {

        // 获取父级菜单最大序号
        int maxSort = getMenuMaxSort(0L, language, Basic.MENU_PARENT);
        if (ObjectUtils.isNull(sort) || sort >= maxSort) {
            // 添加到最后
            sort = maxSort + 1;
        } else {
            // 添加到中间，排序调整
            TSystemMenuConditions conditions1 = TSystemMenuConditions.newInstance()
                    .addFatherId(Basic.ZERO_LONG)
                    .addLanguage(language)
                    .addMinSort(sort);
            List<TSystemMenu> tSystemMenus = selectTSystemMenu(conditions1);
            // 调整原有排序
            for (TSystemMenu tSystemMenu : tSystemMenus) {
                tSystemMenu.setSort(tSystemMenu.getSort() + 1);
                tSystemMenuMapper.updateById(tSystemMenu);
            }
        }
        insertMenu(menuName, Basic.ONE_INT, Basic.ZERO_LONG, menuCode, language, sort, Basic.ON);
    }

    private void addSonMenu(
            Long fatherId,
            String language,
            String menuName,
            String menuCode,
            Integer sort) {

        // 获取子级菜单最大序号
        int maxSort = getMenuMaxSort(fatherId, language, Basic.MENU_SON);
        if (ObjectUtils.isNull(sort) || sort >= maxSort) {
            // 添加到最后
            sort = maxSort + 1;
        } else {
            // 添加到中间，排序调整
            TSystemMenuConditions conditions1 = TSystemMenuConditions.newInstance()
                    .addFatherId(fatherId)
                    .addLanguage(language)
                    .addMinSort(sort);
            List<TSystemMenu> tSystemMenus = selectTSystemMenu(conditions1);
            // 调整原有排序
            for (TSystemMenu tSystemMenu : tSystemMenus) {
                tSystemMenu.setSort(tSystemMenu.getSort() + 1);
                tSystemMenuMapper.updateById(tSystemMenu);
            }
        }
        insertMenu(menuName, Basic.TWO_INT, fatherId, menuCode, language, sort, Basic.ON);

    }


    /**
     * 获取指定顶级模块下的子级模块最大序号
     * @param fatherId
     * @param language
     * @param level
     * @return
     */
    private int getMenuMaxSort(Long fatherId, String language, int level) {
        Integer maxSort = tSystemMenuMapper.getMenuMaxSort(level, language, fatherId);
        return maxSort != null ? maxSort : 0;
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
        tSystemMenuMapper.insert(tSystemMenu);
    }

    private void updateMenu(TSystemMenu tSystemMenu, String menuName,
                            String menuCode,Integer sort,
                            Integer status) {

        if (StrUtils.isNotBlank(menuName))
            tSystemMenu.setMenuName(menuName);

        if (StrUtils.isNotBlank(menuCode))
            tSystemMenu.setMenuCode(menuCode);

        if (ObjectUtils.isNotNull(status))
            tSystemMenu.setStatus(status);

        if (ObjectUtils.isNotNull(sort))
            tSystemMenu.setSort(sort);

        tSystemMenuMapper.updateById(tSystemMenu);
    }

    private List<TSystemMenu> selectTSystemMenu(TSystemMenuConditions conditions) {
        QueryWrapper<TSystemMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", Basic.VAILD);

        if (StrUtils.isNotBlank(conditions.getLanguage())) {
            queryWrapper.eq("language", conditions.getLanguage());
        }

        if (ObjectUtils.isNotNull(conditions.getFatherId()))
            queryWrapper.eq("father_id", conditions.getFatherId());

        if (StrUtils.isNotBlank(conditions.getMenuCode()))
            queryWrapper.eq("menu_code", conditions.getMenuCode());

        // 大于等于
        if (conditions.getMinSort() > 0)
            queryWrapper.ge("sort", conditions.getMinSort());

        // 小于
        if (conditions.getMaxSort() > 0)
            queryWrapper.lt("sort", conditions.getMaxSort());

        // 不等于
        if (ObjectUtils.isNotNull(conditions.getId()))
            queryWrapper.ne("id", conditions.getId());

        queryWrapper.orderByAsc("sort");

        List<TSystemMenu> tSystemMenus = tSystemMenuMapper.selectList(queryWrapper);
        return tSystemMenus;
    }

    private boolean isExistRecords(String menuCode) {
        boolean flag = false;
        TSystemMenuConditions conditions1 = TSystemMenuConditions.newInstance()
                .addMenuCode(menuCode)
                .addLanguage(Strings.LOCALE_ES);
        List<TSystemMenu> tSystemMenus1 = selectTSystemMenu(conditions1);
        if (CollUtils.isNotEmpty(tSystemMenus1)) {
            flag = true;
        }

        TSystemMenuConditions conditions2 = TSystemMenuConditions.newInstance()
                .addMenuCode(menuCode)
                .addLanguage(Strings.LOCALE_ZH);
        List<TSystemMenu> tSystemMenus2 = selectTSystemMenu(conditions2);
        if (CollUtils.isNotEmpty(tSystemMenus2)) {
            flag = true;
        }
        return flag;
    }
}
