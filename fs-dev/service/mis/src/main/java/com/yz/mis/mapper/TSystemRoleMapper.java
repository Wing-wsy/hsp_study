package com.yz.mis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yz.model.entity.TSystemRole;
import org.apache.ibatis.annotations.Param;

public interface TSystemRoleMapper extends BaseMapper<TSystemRole> {

    public Integer getRoleMaxSort(
            @Param("language") String language);
}