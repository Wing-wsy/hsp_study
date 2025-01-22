package com.yz.mis.service.impl.custom;

import com.yz.common.constant.CacheKey;
import com.yz.common.constant.Strings;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.RedisOperator;
import com.yz.mis.service.custom.GeneralConfigService;
import com.yz.mis.service.TResponseErrorEnumsService;
import com.yz.model.entity.TResponseErrorEnums;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeneralConfigServiceImpl implements GeneralConfigService {

    @Resource
    private RedisOperator redisOperator;
    @Resource
    private TResponseErrorEnumsService tResponseErrorEnumsService;

    @Override
    public List<ResponseStatusEnum.ResponseStatusResult> searchResponseResult(String language) {
        List<ResponseStatusEnum.ResponseStatusResult> resultList = new ArrayList<>();
        List<ResponseStatusEnum.ResponseStatusResult> allResponseStatus
                = ResponseStatusEnum.getAllResponseStatus();
        // mis:response:
        String key = "";
        for (ResponseStatusEnum.ResponseStatusResult responseStatus : allResponseStatus) {
            ResponseStatusEnum.ResponseStatusResult result = new ResponseStatusEnum.ResponseStatusResult();
            String code = responseStatus.getCode();
            // mis:response:SUCCESS:es
            key = CacheKey.MIS + CacheKey.T_RESPONSE_ERROR_ENUMS + code + Strings.COLON + language;
            // 1. 查询缓存，命中则直接返回
            String msg = redisOperator.get(key);
            if (ObjectUtils.isNull(msg)) {
                // 2. 缓存没命中，查询数据库，并放入缓存
                TResponseErrorEnums tResponseErrorEnums =
                        tResponseErrorEnumsService.searchResponseByOne(code, language);
                if (ObjectUtils.isNotNull(tResponseErrorEnums)) {
                    redisOperator.set(key, tResponseErrorEnums.getMsg());
                    msg = tResponseErrorEnums.getMsg();
                }
            }
            // 3. 缓存和数据库只要其中命中则返回
            if (msg != null) {
                result.setCode(code);
                result.setMsg(msg);
                result.setStatus(responseStatus.getStatus());
                result.setSuccess(responseStatus.getSuccess());
                resultList.add(result);
            }
        }
        return resultList;
    }
}
