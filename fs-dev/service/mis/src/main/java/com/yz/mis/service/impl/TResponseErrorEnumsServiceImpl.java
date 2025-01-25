package com.yz.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yz.common.constant.Basic;
import com.yz.common.constant.Strings;
import com.yz.common.util.ObjectUtils;
import com.yz.mis.mapper.TResponseErrorEnumsMapper;
import com.yz.mis.service.TResponseErrorEnumsService;
import com.yz.model.bo.mis.UpdateResponseResultBO;
import com.yz.model.entity.TResponseErrorEnums;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public void insertResponseResult(String code, Integer status, String msg, String language) {
        TResponseErrorEnums tResponseErrorEnums = new TResponseErrorEnums();
        tResponseErrorEnums.setCode(code);
        tResponseErrorEnums.setStatus(status);
        tResponseErrorEnums.setMsg(msg);
        tResponseErrorEnums.setLanguage(language);
        Integer sort = getMaxSort(language) + 1;
        tResponseErrorEnums.setSort(sort);
        tResponseErrorEnumsMapper.insert(tResponseErrorEnums);
    }

    @Override
    public void updateResponseResult(UpdateResponseResultBO bo) {
        List<TResponseErrorEnums> records = getRecordsById(bo.getId());
        for (TResponseErrorEnums record : records) {
            String msg = "";
            if (Strings.LOCALE_ES_LOWER.equals(record.getLanguage())) {
                msg = bo.getMsgByES();
            } else {
                msg = bo.getMsgByZH();
            }
            updateResponseResult(record.getId(), bo.getCode(), bo.getStatus(), msg);
        }

    }

    @Override
    public void deleteResponseResult(Long id) {
        LocalDateTime updateTime = LocalDateTime.now();
        List<TResponseErrorEnums> recordsById = getRecordsById(id);
        for (TResponseErrorEnums tResponseErrorEnums : recordsById) {
            tResponseErrorEnumsMapper.deleteTResponseErrorEnumsById(tResponseErrorEnums.getId(), updateTime);
        }
    }

    // 获取最大序号
    private int getMaxSort(String language) {
        return tResponseErrorEnumsMapper.getMaxSort(language);
    }

    // 根据ID获取双语记录
    private List<TResponseErrorEnums> getRecordsById(Long id) {
        return tResponseErrorEnumsMapper.getRecordsById(id);
    }

    public void updateResponseResult(Long id, String code, Integer status, String msg) {
        TResponseErrorEnums tResponseErrorEnums = new TResponseErrorEnums();
        tResponseErrorEnums.setId(id);
        if (ObjectUtils.isNotNull(code))
            tResponseErrorEnums.setCode(code);

        if (ObjectUtils.isNotNull(status))
            tResponseErrorEnums.setStatus(status);

        if (ObjectUtils.isNotNull(msg))
            tResponseErrorEnums.setMsg(msg);

        tResponseErrorEnumsMapper.updateById(tResponseErrorEnums);
    }


}
