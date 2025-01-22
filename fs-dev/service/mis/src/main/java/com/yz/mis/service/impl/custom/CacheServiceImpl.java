package com.yz.mis.service.impl.custom;

import com.yz.common.constant.CacheKey;
import com.yz.common.constant.Strings;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.RedisOperator;
import com.yz.mis.service.CacheService;
import com.yz.mis.service.TResponseErrorEnumsService;
import com.yz.model.entity.TResponseErrorEnums;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    private TResponseErrorEnumsService tResponseErrorEnumsService;
    @Resource
    private RedisOperator redisOperator;

    @Override
    public void refreshResponseResultAll() {
        List<ResponseStatusEnum.ResponseStatusResult> allResponseStatus
                = ResponseStatusEnum.getAllResponseStatus();

        // 两种语言 es zh
        List<String> languages = Arrays.asList(Strings.LOCALE_ES_LOWER,Strings.LOCALE_ZH);
        for (String language : languages) {
            // mis:response:
            String key = "";
            for (ResponseStatusEnum.ResponseStatusResult responseStatus : allResponseStatus) {
                String code = responseStatus.getCode();
                TResponseErrorEnums tResponseErrorEnums =
                        tResponseErrorEnumsService.searchResponseByOne(code, language);
                if (ObjectUtils.isNotNull(tResponseErrorEnums)) {
                    // mis:response:SUCCESS:es
                    key = CacheKey.MIS + CacheKey.T_RESPONSE_ERROR_ENUMS + code + Strings.COLON + language;
                    redisOperator.set(key, tResponseErrorEnums.getMsg());
                }
            }
        }
    }
}
