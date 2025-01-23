package com.yz.mis.service.impl;

import com.yz.mis.mapper.TSystemUserMapper;
import com.yz.mis.service.TSystemUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 测试
 */
@Service
public class TSystemUserServiceImpl implements TSystemUserService {

    @Resource
    private TSystemUserMapper systemUserMapper;

    @Override
    public Set<String> searchUserPermissions(Long systemUserId) {
        Set<String> permissions = systemUserMapper.searchUserPermissions(systemUserId);
        return permissions;
    }

    @Override
    public Set<String> searchUserPermissionsMenus(Long systemUserId) {
        Set<String> permissionsMenus = systemUserMapper.searchUserPermissionsMenus(systemUserId);
        return permissionsMenus;
    }

    @Override
    public Set<String> searchUserMenus(Long systemUserId) {
        Set<String> menus = systemUserMapper.searchUserMenus(systemUserId);
        return menus;
    }
}
