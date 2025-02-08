package com.yz.mis.service.impl;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yz.common.constant.Basic;
import com.yz.common.constant.Strings;
import com.yz.common.exception.GraceException;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.BeanUtils;
import com.yz.common.util.CollUtils;
import com.yz.common.util.JSONUtils;
import com.yz.common.util.ListUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.common.util.page.PageResult;
import com.yz.mis.mapper.TSystemRoleMapper;
import com.yz.mis.service.TSystemPermService;
import com.yz.mis.service.TSystemRoleService;
import com.yz.model.bo.mis.InsertRoleBO;
import com.yz.model.bo.mis.UpdateRoleBO;
import com.yz.model.condition.mis.TSystemRoleConditions;
import com.yz.model.vo.mis.SelectRoleListVO;
import com.yz.model.entity.TSystemPermission;
import com.yz.model.entity.TSystemRole;
import com.yz.model.vo.mis.SelectRolePermTreeVO;
import com.yz.service.base.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TSystemRoleServiceImpl extends BaseService implements TSystemRoleService {

    @Resource
    private TSystemPermService tSystemPermService;
    @Resource
    private TSystemRoleMapper tSystemRoleMapper;

    @Override
    public PageResult<SelectRoleListVO> selectRoleList(TSystemRoleConditions conditions) {
        return selectTSystemRoleListPage(conditions);
    }

    @Override
    public ArrayList<HashMap> selectRolePermTree(String roleCode, Integer status, String language) {
        // 1. 查询当前角色角色
        TSystemRoleConditions conditions1 = TSystemRoleConditions.newInstance()
                .addRoleCode(roleCode)
                .addLanguage(language)
                .addStatus(status);
        List<TSystemRole> tSystemRoles = selectTSystemRoleList(conditions1);
        if (CollUtils.isEmpty(tSystemRoles)) {
            GraceException.display(ResponseStatusEnum.ROLE_SELECT_ERROR);
        }
        String permissions = tSystemRoles.get(0).getPermissions();
        JSONArray permArray = JSONUtils.parseArray(permissions);

        // 2. 获取角色树
        ArrayList<HashMap> permTreeMaps = tSystemPermService.permTree(language, status);
        for (HashMap hashMap : permTreeMaps) {
            // 二级菜单
            ArrayList<HashMap> sonHashMap = (ArrayList)hashMap.get("sonMenuList");
            for (HashMap map : sonHashMap) {
                List<TSystemPermission> tSystemPermissions =(List<TSystemPermission>)map.get("permList");
                List<SelectRolePermTreeVO> selectRolePermTreeVOs = new ArrayList<>();
                for (TSystemPermission tSystemPermission : tSystemPermissions) {
                    SelectRolePermTreeVO vo = BeanUtils.toBean(tSystemPermission, SelectRolePermTreeVO.class);
                    if (permArray.contains(vo.getId())) {
                        vo.setHave(true);
                    } else {
                        vo.setHave(false);
                    }
                    selectRolePermTreeVOs.add(vo);
                }
                map.put("permList", selectRolePermTreeVOs);
            }
        }
        return permTreeMaps;
    }

    @Override
    public void insertRole(InsertRoleBO bo) {
        // 存在则不能重复添加
        boolean existRecords = isExistRecords(bo.getRoleCode());
        if (existRecords) {
            GraceException.display(ResponseStatusEnum.ROLE_INSERT_ERROR);
        }

        List<String> languages = ListUtils.getLanguageList();
        for (String language : languages) {
            String roleName;
            if (Strings.LOCALE_ES.equals(language)) {
                roleName = bo.getRoleNameByES();
            } else {
                roleName = bo.getRoleNameByZH();
            }

            // 获取父级菜单最大序号
            int maxSort = getRoleMaxSort(language);
            TSystemRoleConditions conditions = TSystemRoleConditions.newInstance()
                    .addPermissions(bo.getPermissions())
                    .addRoleName(roleName)
                    .addRoleCode(bo.getRoleCode())
                    .addComment(bo.getComment())
                    .addLanguage(language)
                    .addSort(maxSort + 1);
            insertRoleByConditions(conditions);
        }
    }

    @Override
    public void updateRole(UpdateRoleBO bo) {
        // 修改后的值不能重复
        if (StrUtils.isNotBlank(bo.getRoleCode())) {
            boolean existRecords = isExistRecords(bo.getRoleCode());
            if (existRecords) {
                GraceException.display(ResponseStatusEnum.ROLE_UPDATE_ERROR);
            }
        }

        TSystemRole tSystemRole = tSystemRoleMapper.selectById(bo.getId());
        List<String> languages = ListUtils.getLanguageList();
        for (String language : languages) {
            TSystemRoleConditions conditions1 = TSystemRoleConditions.newInstance()
                    .addRoleCode(tSystemRole.getRoleCode())
                    .addLanguage(language);
            TSystemRole tSystemRole1 = selectTSystemRoleList(conditions1).get(0);
            
            String roleName = null;
            if (Strings.LOCALE_ES.equals(language)) {
                roleName = bo.getRoleNameByES();
            } else {
                roleName = bo.getRoleNameByZH();
            }

            // 2. 只是修改状态、排序
            Integer currentSort = null;
            // 2.1 上移
            if (StrUtils.isNotBlank(bo.getMoveMode()) && Basic.UP.equals(bo.getMoveMode())) {
                if (tSystemRole1.getSort() == Basic.ONE_INT)
                    GraceException.display(ResponseStatusEnum.ROLE_MOVE_UP_ERROR);

                currentSort = tSystemRole1.getSort() - 1;
                // 找到上一个角色,并将序号+1
                TSystemRoleConditions conditions2 = TSystemRoleConditions.newInstance()
                        .addLanguage(language)
                        .addSort(currentSort);
                TSystemRole TSystemRole2 = selectTSystemRoleList(conditions2).get(0);
                TSystemRole2.setSort(TSystemRole2.getSort() + 1);
                tSystemRoleMapper.updateById(TSystemRole2);
            }

            // 2.2 下移
            if (StrUtils.isNotBlank(bo.getMoveMode()) && Basic.DOWN.equals(bo.getMoveMode())) {
                int roleMaxSort = getRoleMaxSort(language);
                if (tSystemRole1.getSort() == roleMaxSort)
                    GraceException.display(ResponseStatusEnum.ROLE_MOVE_DOWN_ERROR);

                currentSort = tSystemRole1.getSort() + 1;
                // 找到下一个角色,并将序号-1
                TSystemRoleConditions conditions3 = TSystemRoleConditions.newInstance()
                        .addLanguage(language)
                        .addSort(currentSort);
                TSystemRole tSystemRole2 = selectTSystemRoleList(conditions3).get(0);
                tSystemRole2.setSort(tSystemRole2.getSort() - 1);
                tSystemRoleMapper.updateById(tSystemRole2);
            }

            TSystemRoleConditions conditions = TSystemRoleConditions.newInstance()
                    .addRoleName(roleName)
                    .addRoleCode(bo.getRoleCode())
                    .addPermissions(bo.getPermissions())
                    .addComment(bo.getComment())
                    .addSort(currentSort)
                    .addStatus(bo.getStatus());
            updateRoleByConditions(tSystemRole1, conditions);
        }
    }

    private List<TSystemRole> selectTSystemRoleList(TSystemRoleConditions conditions) {
        QueryWrapper<TSystemRole> queryWrapper = new QueryWrapper<>();

        if (StrUtils.isNotBlank(conditions.getLanguage())) {
            queryWrapper.eq("language", conditions.getLanguage());
        }

        if (StrUtils.isNotBlank(conditions.getRoleCode())) {
            queryWrapper.eq("role_code", conditions.getRoleCode());
        }

        if (ObjectUtils.isNotNull(conditions.getSort()))
            queryWrapper.eq("sort", conditions.getSort());

        if (ObjectUtils.isNotNull(conditions.getStatus()) && conditions.getStatus() == Basic.NORMAL)
            queryWrapper.eq("status", conditions.getStatus());

        queryWrapper.orderByAsc("sort");

        List<TSystemRole> tSystemRoles = tSystemRoleMapper.selectList(queryWrapper);
        return tSystemRoles;
    }

    // 分页
    private PageResult<SelectRoleListVO> selectTSystemRoleListPage(TSystemRoleConditions conditions) {
        QueryWrapper<TSystemRole> queryWrapper = new QueryWrapper<>();

        if (StrUtils.isNotBlank(conditions.getLanguage())) {
            queryWrapper.eq("language", conditions.getLanguage());
        }

        if (ObjectUtils.isNotNull(conditions.getStatus()) && conditions.getStatus() == Basic.NORMAL)
            queryWrapper.eq("status", conditions.getStatus());

        queryWrapper.orderByAsc("sort");

        // 设置分页参数
        Page<TSystemRole> pageInfo = new Page<>(conditions.getPage(), conditions.getPageSize());
        tSystemRoleMapper.selectPage(pageInfo,queryWrapper);

        List<TSystemRole> tSystemRoles = pageInfo.getRecords();
        List<SelectRoleListVO> dtoList = BeanUtils.convertBeanList(tSystemRoles, SelectRoleListVO.class);

        Page<SelectRoleListVO> selectRoleListVOPage = convertPage(pageInfo, dtoList);
        return setPagePlus(selectRoleListVOPage);
    }

    private void insertRoleByConditions(TSystemRoleConditions conditions) {
        TSystemRole tSystemRole = new TSystemRole();
        tSystemRole.setRoleName(conditions.getRoleName());
        tSystemRole.setRoleCode(conditions.getRoleCode());
        tSystemRole.setComment(conditions.getComment());
        tSystemRole.setLanguage(conditions.getLanguage());
        tSystemRole.setStatus(Basic.NORMAL);
        tSystemRole.setSort(conditions.getSort());
        tSystemRole.setPermissions(conditions.getPermissions());
        tSystemRoleMapper.insert(tSystemRole);
    }

    private boolean isExistRecords(String roleCode) {
        boolean flag = false;
        TSystemRoleConditions conditions1 = TSystemRoleConditions.newInstance()
                .addRoleCode(roleCode)
                .addLanguage(Strings.LOCALE_ES);
        List<TSystemRole> tSystemRoles1 = selectTSystemRoleList(conditions1);
        if (CollUtils.isNotEmpty(tSystemRoles1)) {
            flag = true;
        }

        TSystemRoleConditions conditions2 = TSystemRoleConditions.newInstance()
                .addRoleCode(roleCode)
                .addLanguage(Strings.LOCALE_ZH);
        List<TSystemRole> tSystemRoles2 = selectTSystemRoleList(conditions2);
        if (CollUtils.isNotEmpty(tSystemRoles2)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取最大序号
     */
    private int getRoleMaxSort(String language) {
        Integer maxSort = tSystemRoleMapper.getRoleMaxSort(language);
        return maxSort != null ? maxSort : 0;
    }

    private void updateRoleByConditions(TSystemRole tSystemRole, TSystemRoleConditions conditions) {

        if (StrUtils.isNotBlank(conditions.getRoleName()))
            tSystemRole.setRoleName(conditions.getRoleName());

        if (StrUtils.isNotBlank(conditions.getRoleCode()))
            tSystemRole.setRoleCode(conditions.getRoleCode());

        if (StrUtils.isNotBlank(conditions.getPermissions()))
            tSystemRole.setPermissions(conditions.getPermissions());

        if (StrUtils.isNotBlank(conditions.getComment()))
            tSystemRole.setComment(conditions.getComment());

        if (ObjectUtils.isNotNull(conditions.getStatus()))
            tSystemRole.setStatus(conditions.getStatus());

        if (ObjectUtils.isNotNull(conditions.getSort()))
            tSystemRole.setSort(conditions.getSort());

        tSystemRoleMapper.updateById(tSystemRole);
    }
}
