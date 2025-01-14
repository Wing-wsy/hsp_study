package com.yz.cst.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yz.model.entity.TUser;

/**
 * 例子
 */
//@Mapper
public interface TUserMapper extends BaseMapper<TUser> {

    public TUser getById(Long userId);
}
