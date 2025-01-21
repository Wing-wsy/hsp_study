package com.yz.mis.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.mis.service.GeneralConfigService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralConfigServiceImpl implements GeneralConfigService {

    @Override
    public List<ResponseStatusEnum.ResponseStatusResult> searchResponseResult(String language) {
        List<ResponseStatusEnum.ResponseStatusResult> resultList = new ArrayList<>();
        List<ResponseStatusEnum.ResponseStatusResult> allResponseStatus
                = ResponseStatusEnum.getAllResponseStatus();
        for (ResponseStatusEnum.ResponseStatusResult responseStatus : allResponseStatus) {
            // 1. 查询缓存，命中则直接返回

            // 2. 缓存没命中，查询数据库，放入缓存

        }

        return resultList;
    }
}
