package com.yz.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yz.common.constant.Basic;
import com.yz.common.constant.Strings;
import com.yz.common.exception.GraceException;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.BeanUtils;
import com.yz.common.util.CollUtils;
import com.yz.common.util.IntUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.StrUtils;
import com.yz.common.util.page.PageResult;
import com.yz.mis.mapper.TSystemUserMapper;
import com.yz.mis.service.TSystemUserService;
import com.yz.model.bo.mis.InsertUserBO;
import com.yz.model.bo.mis.UpdateUserBO;
import com.yz.model.condition.mis.TSystemRoleConditions;
import com.yz.model.condition.mis.TSystemUserConditions;
import com.yz.model.entity.TSystemPermission;
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

    @Override
    public void insertUser(InsertUserBO bo) {
        // 存在相同用户名则不能重复添加
        boolean existRecords = isExistRecords(bo.getUsername());
        if (existRecords)
            GraceException.display(ResponseStatusEnum.USER_INSERT_ERROR);

        // 超级管理员只能有一个
        if (Basic.IS_ROOT == IntUtils.parseInt(bo.getRoot())) {
            boolean existRoot = isExistRoot();
            if (existRoot)
                GraceException.display(ResponseStatusEnum.USER_INSERT_ROOT_ERROR);
        }

        TSystemUserConditions conditions = TSystemUserConditions.newInstance()
                .addUsername(bo.getUsername())
                .addPassword(bo.getPassword())
                .addName(bo.getName())
                .addSex(IntUtils.parseInt(bo.getSex()))
                .addMobile(bo.getMobile())
                .addEmail(bo.getEmail())
                .addRoleCode(bo.getRole())
                .addRoot(IntUtils.parseInt(bo.getRoot()))
                .addDeptCode(bo.getDeptCode())
                .addStatus(IntUtils.parseInt(bo.getStatus()));
        insertUserByConditions(conditions);
    }

    @Override
    public void deleteUser(Long id) {
        TSystemUser tSystemUser = tSystemUserMapper.selectById(id);
        if (ObjectUtils.isNull(tSystemUser)) {
            GraceException.display(ResponseStatusEnum.USER_DELETE_ERROR);
        }
        tSystemUser.setIsDelete(Basic.DELETE);
        tSystemUserMapper.updateById(tSystemUser);
    }

    @Override
    public void updateUser(UpdateUserBO bo) {
        TSystemUser tSystemUser = tSystemUserMapper.selectById(bo.getId());
        if (ObjectUtils.isNull(tSystemUser)) {
            GraceException.display(ResponseStatusEnum.USER_UPDATE_NOT_FIND_ERROR);
        }

        // 超级管理员只能有一个
        if (StrUtils.isNotBlank(bo.getRoot()) && Basic.IS_ROOT == IntUtils.parseInt(bo.getRoot())) {
            boolean existRoot = isExistRoot();
            if (existRoot)
                GraceException.display(ResponseStatusEnum.USER_UPDATE_ROOT_ERROR);
        }

        TSystemUserConditions conditions = TSystemUserConditions.newInstance()
                .addUsername(bo.getUsername())
                .addPassword(bo.getPassword())
                .addName(bo.getName())
                .addSex(IntUtils.parseInt(bo.getSex()))
                .addMobile(bo.getMobile())
                .addEmail(bo.getEmail())
                .addRoleCode(bo.getRole())
                .addRoot(IntUtils.parseInt(bo.getRoot()))
                .addDeptCode(bo.getDeptCode())
                .addStatus(IntUtils.parseInt(bo.getStatus()));
        updateUserByConditions(tSystemUser, conditions);

    }

    private List<TSystemUser> selectTSystemUserList(TSystemUserConditions conditions) {
        QueryWrapper<TSystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", Basic.VAILD);

        if (ObjectUtils.isNotNull(conditions.getRoot()))
            queryWrapper.eq("root", conditions.getRoot());

        if (ObjectUtils.isNotNull(conditions.getStatus()) && conditions.getStatus() != Basic.FOUR_INT)
            queryWrapper.eq("status", conditions.getStatus());

        // 模糊查询
        if (StrUtils.isNotBlank(conditions.getUsername()))
            queryWrapper.like("username", conditions.getUsername());

        // 精确查询
        if (StrUtils.isNotBlank(conditions.getUsernameByExact()))
            queryWrapper.eq("username", conditions.getUsernameByExact());

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

    private boolean isExistRecords(String username) {
        boolean flag = false;
        TSystemUserConditions conditions1 = TSystemUserConditions.newInstance()
                .addUsernameByExact(username);
        List<TSystemUser> tSystemUsers1 = selectTSystemUserList(conditions1);
        if (CollUtils.isNotEmpty(tSystemUsers1)) {
            flag = true;
        }
        return flag;
    }

    private boolean isExistRoot() {
        boolean flag = false;
        TSystemUserConditions conditions = TSystemUserConditions.newInstance()
                .addRoot(1);
        List<TSystemUser> tSystemUsers = selectTSystemUserList(conditions);
        if (CollUtils.isNotEmpty(tSystemUsers)) {
            flag = true;
        }
        return flag;
    }

    private void insertUserByConditions(TSystemUserConditions conditions) {
        TSystemUser tSystemUser = new TSystemUser();
        tSystemUser.setUsername(conditions.getUsername());
        tSystemUser.setPassword(conditions.getPassword());
        tSystemUser.setName(conditions.getName());
        tSystemUser.setSex(conditions.getSex());
        tSystemUser.setMobile(conditions.getMobile());
        tSystemUser.setEmail(conditions.getEmail());
        tSystemUser.setRole(conditions.getRoleCode());
        tSystemUser.setRoot(conditions.getRoot());
        tSystemUser.setDeptCode(conditions.getDeptCode());
        tSystemUser.setStatus(conditions.getStatus());
        tSystemUserMapper.insert(tSystemUser);
    }

    private void updateUserByConditions(TSystemUser tSystemUser, TSystemUserConditions conditions) {

        if (StrUtils.isNotBlank(conditions.getUsername()))
            tSystemUser.setUsername(conditions.getUsername());

        if (StrUtils.isNotBlank(conditions.getPassword()))
            tSystemUser.setPassword(conditions.getPassword());

        if (StrUtils.isNotBlank(conditions.getName()))
            tSystemUser.setName(conditions.getName());

        if (ObjectUtils.isNotNull(conditions.getSex()))
            tSystemUser.setSex(conditions.getSex());

        if (StrUtils.isNotBlank(conditions.getMobile()))
            tSystemUser.setMobile(conditions.getMobile());

        if (StrUtils.isNotBlank(conditions.getEmail()))
            tSystemUser.setEmail(conditions.getEmail());

        if (StrUtils.isNotBlank(conditions.getRoleCode()))
            tSystemUser.setRole(conditions.getRoleCode());

        if (ObjectUtils.isNotNull(conditions.getRoot()))
            tSystemUser.setRoot(conditions.getRoot());

        if (StrUtils.isNotBlank(conditions.getDeptCode()))
            tSystemUser.setDeptCode(conditions.getDeptCode());

        if (ObjectUtils.isNotNull(conditions.getStatus()))
            tSystemUser.setStatus(conditions.getStatus());

        tSystemUserMapper.updateById(tSystemUser);
    }
}
