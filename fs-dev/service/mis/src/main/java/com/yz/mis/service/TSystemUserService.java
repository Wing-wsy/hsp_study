package com.yz.mis.service;

import com.yz.common.util.page.PageResult;
import com.yz.model.condition.mis.TSystemUserConditions;
import com.yz.model.vo.mis.SelectUserListVO;

import java.util.Set;

/**
 * 测试
 */
public interface TSystemUserService {

    /**
     * 查询系统用户权限
     * @param systemUserId 系统用户ID
     * @return 权限列表
     */
    public Set<String> searchUserPermissions(Long systemUserId);

    /**
     * 查询系统用户权限菜单
     * @param systemUserId 系统用户ID
     * @return 菜单列表
     */
    public Set<String> searchUserPermissionsMenus(Long systemUserId);

    /**
     * 查询系统用户菜单权限
     * @param systemUserId 系统用户ID
     * @return 菜单列表
     */
    public Set<String> searchUserMenus(Long systemUserId);

    /**
     * 查询用户列表
     */
    public PageResult<SelectUserListVO> selectUserList(TSystemUserConditions conditions);

}
