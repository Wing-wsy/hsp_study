package com.yz.mis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yz.model.condition.mis.TSystemUserConditions;
import com.yz.model.entity.TSystemUser;
import com.yz.model.vo.mis.SelectUserListVO;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface TSystemUserMapper extends BaseMapper<TSystemUser> {

    public Set<String> searchUserPermissions(Long systemUserId);

    public Set<String> searchUserPermissionsMenus(Long systemUserId);

    public Set<String> searchUserMenus(Long systemUserId);

    public Page<SelectUserListVO> selectTSystemUserListByPage(@Param("page") Page<SelectUserListVO> page,
                                                              @Param("conditions")TSystemUserConditions conditions);
}
