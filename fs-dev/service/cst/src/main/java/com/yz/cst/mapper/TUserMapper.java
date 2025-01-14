package com.yz.cst.mapper;

//import com.yz.service.base.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yz.model.entity.TUser;
//import org.apache.ibatis.annotations.Mapper;

/**
 * 例子
 */
//@Mapper
public interface TUserMapper extends BaseMapper<TUser> {
//public interface TUserMapper {

    public TUser getById(Long userId);
}
