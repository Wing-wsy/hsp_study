package com.yz.mis.service;

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
     * 查询系统用户菜单权限
     * @param systemUserId 系统用户ID
     * @return 菜单列表
     */
    public Set<String> searchUserMenus(Long systemUserId);

}
