package com.yz.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.common.constant.Basic;
import com.yz.common.constant.Strings;
import com.yz.common.exception.GraceException;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.CollUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.mis.mapper.TSystemPermissionMapper;
import com.yz.mis.service.TSystemMenuService;
import com.yz.mis.service.TSystemPermService;
import com.yz.model.bo.mis.AddPermBO;
import com.yz.model.bo.mis.UpdatePermBO;
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

                List<TSystemPermission> tSystemPermissions = selectTSystemPermission(null, menuCode, status, null);
                map.put("permList", tSystemPermissions);
            }
        }
        return hashMaps;
    }

    @Override
    public void addPerm(AddPermBO bo) {
        String menuCode = bo.getMenuCode();
        String permissionName = menuCode + Strings.COLON + bo.getPermName();
        // 1. 判断当前权限是否已经存在，存在则不能重复添加
        List<TSystemPermission> tSystemPermissions = selectTSystemPermission(permissionName, null, null, null);
        if (CollUtils.isNotEmpty(tSystemPermissions)) {
            GraceException.display(ResponseStatusEnum.PERM_INSERT_ERROR);
        }
        // 2. 获取最大序号
        int permMaxSort = getPermMaxSort(menuCode);
        // 3. 新增一条记录
        insertPermission(permissionName, menuCode, permMaxSort + 1);
    }

    @Override
    public void deletePerm(Long id) {
        TSystemPermission tSystemPermission = tSystemPermissionMapper.selectById(id);
        if (ObjectUtils.isNull(tSystemPermission)) {
            GraceException.display(ResponseStatusEnum.PERM_DELETE_ERROR);
        }
        tSystemPermission.setIsDelete(Basic.DELETE);
        tSystemPermissionMapper.updateById(tSystemPermission);
    }

    @Override
    public void updatePerm(UpdatePermBO bo) {
        TSystemPermission tSystemPermission = tSystemPermissionMapper.selectById(bo.getId());
        if (ObjectUtils.isNull(tSystemPermission)) {
            GraceException.display(ResponseStatusEnum.PERM_UPDATE_NOT_FIND_ERROR);
        }

        // 1. 判断是否需要修改权限名称
        if (StrUtils.isNotBlank(bo.getMenuCode())) {
            String permissionName = bo.getMenuCode() + Strings.COLON + bo.getPermissionName();

            // 1.1 判断当前权限是否已经存在，存在则不能修改
            List<TSystemPermission> tSystemPermissions = selectTSystemPermission(permissionName, null, null, null);
            if (CollUtils.isNotEmpty(tSystemPermissions)) {
                GraceException.display(ResponseStatusEnum.PERM_UPDATE_ERROR);
            }

            // 1.2 修改权限
            updatePermByConditions(tSystemPermission, permissionName,
                    bo.getMenuCode(), null, null);
        } else {
            // 2. 只是修改状态、排序
            Integer currentSort = null;
            // 2.1 上移
            if (StrUtils.isNotBlank(bo.getMoveMode()) && Basic.UP.equals(bo.getMoveMode())) {
                if (tSystemPermission.getSort() == Basic.ONE_INT)
                    GraceException.display(ResponseStatusEnum.PERM_MOVE_UP_ERROR);

                currentSort = tSystemPermission.getSort() - 1;
                // 找到上一个权限,并将序号+1
                TSystemPermission tSystemPermission1 = selectTSystemPermission(null, tSystemPermission.getMenuCode(),
                        null, currentSort).get(0);

                tSystemPermission1.setSort(tSystemPermission1.getSort() + 1);
                tSystemPermissionMapper.updateById(tSystemPermission1);
            }

            // 2.2 下移
            if (StrUtils.isNotBlank(bo.getMoveMode()) && Basic.DOWN.equals(bo.getMoveMode())) {
                int permMaxSort = getPermMaxSort(tSystemPermission.getMenuCode());
                if (tSystemPermission.getSort() == permMaxSort)
                    GraceException.display(ResponseStatusEnum.PERM_MOVE_DOWN_ERROR);

                currentSort = tSystemPermission.getSort() + 1;

                // 找到下一个权限,并将序号-1
                TSystemPermission tSystemPermission2 = selectTSystemPermission(null, tSystemPermission.getMenuCode(), null,
                        currentSort).get(0);

                tSystemPermission2.setSort(tSystemPermission2.getSort() - 1);
                tSystemPermissionMapper.updateById(tSystemPermission2);
            }

            // 修改权限序号
            if (ObjectUtils.isNotNull(currentSort)) {
                tSystemPermission.setSort(currentSort);
            }

            // 修改权限状态
            if (ObjectUtils.isNotNull(bo.getStatus())) {
                tSystemPermission.setStatus(bo.getStatus());
            }
            tSystemPermissionMapper.updateById(tSystemPermission);

        }
    }

    private List<TSystemPermission> selectTSystemPermission(String permissionName, String menuCode, Integer status, Integer sort) {
        QueryWrapper<TSystemPermission> selectWrapper = new QueryWrapper<>();
        selectWrapper.eq("is_delete", Basic.VAILD);

        if (StrUtils.isNotBlank(permissionName))
            selectWrapper.eq("permission_name", permissionName);

        if (StrUtils.isNotBlank(menuCode))
            selectWrapper.eq("menu_code", menuCode);

        if (ObjectUtils.isNotNull(sort))
            selectWrapper.eq("sort", sort);

        if (ObjectUtils.isNotNull(status) && status == Basic.NORMAL)
            selectWrapper.eq("status", status);
        
        selectWrapper.orderByAsc("sort");
        List<TSystemPermission> tSystemPermissions = tSystemPermissionMapper.selectList(selectWrapper);
        return tSystemPermissions;
    }

    private void insertPermission(String permissionName, String menuCode, Integer sort) {
        TSystemPermission tSystemPermission = new TSystemPermission();
        tSystemPermission.setPermissionName(permissionName);
        tSystemPermission.setModuleId(Basic.ONE_INT);
        tSystemPermission.setActionId(Basic.ONE_INT);
        tSystemPermission.setMenuCode(menuCode);
        tSystemPermission.setStatus(Basic.NORMAL);
        tSystemPermission.setSort(sort);
        tSystemPermission.setIsDelete(Basic.VAILD);
        tSystemPermissionMapper.insert(tSystemPermission);
    }

    private int getPermMaxSort(String menuCode) {
        Integer maxSort = tSystemPermissionMapper.getPermMaxSort(menuCode);
        return maxSort != null ? maxSort : 0;
    }

    private void updatePermByConditions(TSystemPermission tSystemPermission, String permissionName,
                            String menuCode, Integer sort,
                            Integer status) {

        if (StrUtils.isNotBlank(permissionName))
            tSystemPermission.setPermissionName(permissionName);

        if (StrUtils.isNotBlank(menuCode))
            tSystemPermission.setMenuCode(menuCode);

        if (ObjectUtils.isNotNull(status))
            tSystemPermission.setStatus(status);

        if (ObjectUtils.isNotNull(sort))
            tSystemPermission.setSort(sort);

        tSystemPermissionMapper.updateById(tSystemPermission);
    }

}
