package com.yz.mis.service.impl;

import com.yz.mis.mapper.SystemUserMapper;
import com.yz.mis.service.SystemUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 测试
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    public Set<String> searchUserPermissions(Long systemUserId) {
        Set<String> permissions = systemUserMapper.searchUserPermissions(systemUserId);
        return permissions;
    }

    @Override
    public Set<String> searchUserMenus(Long systemUserId) {
        Set<String> menus = systemUserMapper.searchUserMenus(systemUserId);
        return menus;
    }
}
