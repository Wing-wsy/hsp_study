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
import com.yz.model.entity.TSystemMenu;
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
        // 1. 查询当前角色权限
        List<TSystemRole> tSystemRoles = selectTSystemRole(roleCode, language, status);
        if (CollUtils.isEmpty(tSystemRoles)) {
            GraceException.display(ResponseStatusEnum.ROLE_SELECT_ERROR);
        }
        String permissions = tSystemRoles.get(0).getPermissions();
        JSONArray permArray = JSONUtils.parseArray(permissions);

        // 2. 获取权限树
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
            if (Strings.LOCALE_ES_LOWER.equals(language)) {
                roleName = bo.getRoleNameByES();
            } else {
                roleName = bo.getRoleNameByZH();
            }

            // 获取父级菜单最大序号
            int maxSort = getRoleMaxSort(language);
            insertRoleByConditions(bo.getPermissions(), roleName, bo.getRoleCode(), bo.getComment(), language, maxSort + 1);
        }
    }

    private List<TSystemRole> selectTSystemRole(String roleCode, String language, Integer status) {
        QueryWrapper<TSystemRole> selectWrapper = new QueryWrapper<>();

        if (StrUtils.isNotBlank(language)) {
            selectWrapper.eq("language", language);
        }
        
        if (StrUtils.isNotBlank(roleCode)) {
            selectWrapper.eq("role_code", roleCode);
        }

        if (ObjectUtils.isNotNull(status) && status == Basic.NORMAL)
            selectWrapper.eq("status", status);

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
        Page<TSystemRole> pageInfoTOrder = new Page<>(page, pageSize);
        tSystemRoleMapper.selectPage(pageInfoTOrder,selectWrapper);

        List<TSystemRole> tSystemRoles = pageInfoTOrder.getRecords();
        List<SelectRoleListVO> dtoList = BeanUtils.convertBeanList(tSystemRoles, SelectRoleListVO.class);

        Page<SelectRoleListVO> selectRoleListVOPage = convertPage(pageInfoTOrder, dtoList);
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
        List<TSystemRole> tSystemRoles1 = selectTSystemRole(roleCode, Strings.LOCALE_ES_LOWER, null);
        if (CollUtils.isNotEmpty(tSystemRoles1)) {
            flag = true;
        }
        List<TSystemRole> tSystemRoles2 = selectTSystemRole(roleCode, Strings.LOCALE_ZH, null);
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
}
