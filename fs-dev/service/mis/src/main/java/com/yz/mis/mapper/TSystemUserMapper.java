package com.yz.mis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yz.model.entity.TSystemUser;

import java.util.Set;

public interface TSystemUserMapper extends BaseMapper<TSystemUser> {

    public Set<String> searchUserPermissions(Long systemUserId);

    public Set<String> searchUserMenus(Long systemUserId);
}
