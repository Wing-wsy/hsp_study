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
    public PageResult<SelectRoleListVO> selectRoleList(String language, Integer status,
                                                       Integer page, Integer pageSize) {
        return selectTSystemRoleListPage(language, status, page, pageSize);
    }

    @Override
    public ArrayList<HashMap> selectRolePermTree(String roleCode, Integer status, String language) {
        // 1. 查询当前角色角色
        TSystemRoleConditions conditions1 = TSystemRoleConditions.newInstance()
                .addRoleCode(roleCode)
                .addLanguage(language)
                .addStatus(status);
        List<TSystemRole> tSystemRoles = selectTSystemRole(conditions1);
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
            insertRoleByConditions(bo.getPermissions(), roleName, bo.getRoleCode(), bo.getComment(), language, maxSort + 1);
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
            TSystemRole tSystemRole1 = selectTSystemRole(conditions1).get(0);
            
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
                TSystemRole TSystemRole2 = selectTSystemRole(conditions2).get(0);
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
                TSystemRole tSystemRole2 = selectTSystemRole(conditions3).get(0);
                tSystemRole2.setSort(tSystemRole2.getSort() - 1);
                tSystemRoleMapper.updateById(tSystemRole2);
            }

            updateRoleByConditions(tSystemRole1, roleName,
                    bo.getRoleCode(), bo.getPermissions(),
                    bo.getComment(), currentSort, bo.getStatus());
        }
    }

    private List<TSystemRole> selectTSystemRole(TSystemRoleConditions conditions) {
        QueryWrapper<TSystemRole> selectWrapper = new QueryWrapper<>();

        if (StrUtils.isNotBlank(conditions.getLanguage())) {
            selectWrapper.eq("language", conditions.getLanguage());
        }

        if (StrUtils.isNotBlank(conditions.getRoleCode())) {
            selectWrapper.eq("role_code", conditions.getRoleCode());
        }

        if (ObjectUtils.isNotNull(conditions.getSort()))
            selectWrapper.eq("sort", conditions.getSort());

        if (ObjectUtils.isNotNull(conditions.getStatus()) && conditions.getStatus() == Basic.NORMAL)
            selectWrapper.eq("status", conditions.getStatus());

        selectWrapper.orderByAsc("sort");

        List<TSystemRole> tSystemRoles = tSystemRoleMapper.selectList(selectWrapper);
        return tSystemRoles;
    }

    // 分页
    private PageResult<SelectRoleListVO> selectTSystemRoleListPage(String language, Integer status, Integer page, Integer pageSize) {
        QueryWrapper<TSystemRole> selectWrapper = new QueryWrapper<>();

        if (StrUtils.isNotBlank(language)) {
            selectWrapper.eq("language", language);
        }

        if (ObjectUtils.isNotNull(status) && status == Basic.NORMAL)
            selectWrapper.eq("status", status);

        selectWrapper.orderByAsc("sort");

        // 设置分页参数
        Page<TSystemRole> pageInfo = new Page<>(page, pageSize);
        tSystemRoleMapper.selectPage(pageInfo,selectWrapper);

        List<TSystemRole> tSystemRoles = pageInfo.getRecords();
        List<SelectRoleListVO> dtoList = BeanUtils.convertBeanList(tSystemRoles, SelectRoleListVO.class);

        Page<SelectRoleListVO> selectRoleListVOPage = convertPage(pageInfo, dtoList);
        return setPagePlus(selectRoleListVOPage);
    }

    private void insertRoleByConditions(String permissions, String roleName, String roleCode, String comment, String language, Integer sort) {
        TSystemRole tSystemRole = new TSystemRole();
        tSystemRole.setRoleName(roleName);
        tSystemRole.setRoleCode(roleCode);
        tSystemRole.setComment(comment);
        tSystemRole.setLanguage(language);
        tSystemRole.setStatus(Basic.NORMAL);
        tSystemRole.setSort(sort);
        tSystemRole.setPermissions(permissions);
        tSystemRoleMapper.insert(tSystemRole);
    }

    private boolean isExistRecords(String roleCode) {
        boolean flag = false;
        TSystemRoleConditions conditions1 = TSystemRoleConditions.newInstance()
                .addRoleCode(roleCode)
                .addLanguage(Strings.LOCALE_ES);
        List<TSystemRole> tSystemRoles1 = selectTSystemRole(conditions1);
        if (CollUtils.isNotEmpty(tSystemRoles1)) {
            flag = true;
        }

        TSystemRoleConditions conditions2 = TSystemRoleConditions.newInstance()
                .addRoleCode(roleCode)
                .addLanguage(Strings.LOCALE_ZH);
        List<TSystemRole> tSystemRoles2 = selectTSystemRole(conditions2);
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

    private void updateRoleByConditions(TSystemRole tSystemRole, String roleName,
                                        String roleCode, String permissions,
                                        String comment, Integer sort,
                                        Integer status) {

        if (StrUtils.isNotBlank(roleName))
            tSystemRole.setRoleName(roleName);

        if (StrUtils.isNotBlank(roleCode))
            tSystemRole.setRoleCode(roleCode);

        if (StrUtils.isNotBlank(permissions))
            tSystemRole.setPermissions(permissions);

        if (StrUtils.isNotBlank(comment))
            tSystemRole.setComment(comment);

        if (ObjectUtils.isNotNull(status))
            tSystemRole.setStatus(status);

        if (ObjectUtils.isNotNull(sort))
            tSystemRole.setSort(sort);

        tSystemRoleMapper.updateById(tSystemRole);
    }
}
