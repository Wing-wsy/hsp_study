package com.yz.mis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yz.model.entity.TSystemMenu;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;

public interface TSystemMenuMapper extends BaseMapper<TSystemMenu> {

    public ArrayList<HashMap> searchMenuTree(
            @Param("level") int level,
            @Param("language") String language,
            @Param("fatherId") Long fatherId);
}