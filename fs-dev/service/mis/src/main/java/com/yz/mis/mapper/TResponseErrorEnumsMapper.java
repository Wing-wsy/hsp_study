package com.yz.mis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yz.model.entity.TResponseErrorEnums;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TResponseErrorEnumsMapper extends BaseMapper<TResponseErrorEnums> {
    public int getMaxSort(
            @Param("language") String language);
    public void deleteTResponseErrorEnumsById(
            @Param("id") Long id,
            @Param("updateTime") LocalDateTime updateTime);

    public List<TResponseErrorEnums> getRecordsById(
            @Param("id") Long id);
}