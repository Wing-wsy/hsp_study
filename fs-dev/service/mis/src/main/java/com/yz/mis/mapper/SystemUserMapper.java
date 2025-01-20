package com.yz.mis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yz.model.entity.SystemUser;

import java.util.Set;

public interface SystemUserMapper extends BaseMapper<SystemUser> {

    public Set<String> searchUserPermissions(Long systemUserId);

    public Set<String> searchUserMenus(Long systemUserId);
}
