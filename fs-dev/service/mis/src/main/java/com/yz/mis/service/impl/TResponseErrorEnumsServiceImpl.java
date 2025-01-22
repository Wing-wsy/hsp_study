package com.yz.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.mis.mapper.TResponseErrorEnumsMapper;
import com.yz.mis.service.TResponseErrorEnumsService;
import com.yz.model.entity.TResponseErrorEnums;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TResponseErrorEnumsServiceImpl implements TResponseErrorEnumsService {

    @Resource
    private TResponseErrorEnumsMapper tResponseErrorEnumsMapper;

    @Override
    public TResponseErrorEnums searchResponseByOne(String code, String language) {

        QueryWrapper<TResponseErrorEnums> selectWrapper = new QueryWrapper<>();
        selectWrapper.eq("code", code);
        selectWrapper.eq("language", language);
        return tResponseErrorEnumsMapper.selectOne(selectWrapper);

    }




}
