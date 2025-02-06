package com.yz.mis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yz.model.entity.TSystemDept;
import org.apache.ibatis.annotations.Param;

public interface TSystemDeptMapper extends BaseMapper<TSystemDept> {

    public Integer getDeptMaxSort(
            @Param("language") String language,
            @Param("parentId") Long parentId);

}