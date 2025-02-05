package com.yz.mis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yz.model.entity.TSystemPermission;
import org.apache.ibatis.annotations.Param;

public interface TSystemPermissionMapper extends BaseMapper<TSystemPermission> {

    public Integer getPermMaxSort(
            @Param("menuCode") String menuCode);

}