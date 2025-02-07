package com.yz.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yz.common.constant.Basic;
import com.yz.common.util.BeanUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.common.util.page.PageResult;
import com.yz.mis.mapper.TSystemUserMapper;
import com.yz.mis.service.TSystemUserService;
import com.yz.model.condition.mis.TSystemRoleConditions;
import com.yz.model.condition.mis.TSystemUserConditions;
import com.yz.model.entity.TSystemRole;
import com.yz.model.entity.TSystemUser;
import com.yz.model.vo.mis.SelectRoleListVO;
import com.yz.model.vo.mis.SelectUserListVO;
import com.yz.service.base.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 测试
 */
@Service
public class TSystemUserServiceImpl extends BaseService implements TSystemUserService {

    @Resource
    private TSystemUserMapper tSystemUserMapper;

    @Override
    public Set<String> searchUserPermissions(Long systemUserId) {
        Set<String> permissions = tSystemUserMapper.searchUserPermissions(systemUserId);
        return permissions;
    }

    @Override
    public Set<String> searchUserPermissionsMenus(Long systemUserId) {
        Set<String> permissionsMenus = tSystemUserMapper.searchUserPermissionsMenus(systemUserId);
        return permissionsMenus;
    }

    @Override
    public Set<String> searchUserMenus(Long systemUserId) {
        Set<String> menus = tSystemUserMapper.searchUserMenus(systemUserId);
        return menus;
    }

    @Override
    public PageResult<SelectUserListVO> selectUserList(TSystemUserConditions conditions) {
        return selectTSystemUserListPage(conditions);
    }

    private List<TSystemUser> selectTSystemUserList(TSystemUserConditions conditions) {
        QueryWrapper<TSystemUser> queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotNull(conditions.getStatus()) && conditions.getStatus() != Basic.FOUR_INT)
            queryWrapper.eq("status", conditions.getStatus());

        // 模糊查询
        if (StrUtils.isNotBlank(conditions.getUsername()))
            queryWrapper.like("username", conditions.getUsername());

        // 模糊查询
        if (StrUtils.isNotBlank(conditions.getName()))
            queryWrapper.like("name", conditions.getName());

        // 模糊查询
        if (StrUtils.isNotBlank(conditions.getMobile()))
            queryWrapper.like("mobile", conditions.getMobile());

        if (ObjectUtils.isNotNull(conditions.getDeptCode()))
            queryWrapper.eq("deptCode", conditions.getDeptCode());

        queryWrapper.orderByAsc("username");

        List<TSystemUser> tSystemUsers = tSystemUserMapper.selectList(queryWrapper);
        return tSystemUsers;
    }

    // 分页
    private PageResult<SelectUserListVO> selectTSystemUserListPage(TSystemUserConditions conditions) {

        // 设置分页参数
        Page<SelectUserListVO> pageInfo = new Page<>(conditions.getPage(), conditions.getPageSize());
        tSystemUserMapper.selectTSystemUserListByPage(pageInfo, conditions);
        return setPagePlus(pageInfo);
    }
}
