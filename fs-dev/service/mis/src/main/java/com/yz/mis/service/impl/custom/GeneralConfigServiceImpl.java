package com.yz.mis.service.impl.custom;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yz.common.constant.CacheKey;
import com.yz.common.constant.Strings;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.ListUtils;
import com.yz.common.util.ObjectUtils;
import com.yz.common.util.RedisOperator;
import com.yz.common.util.StrUtils;
import com.yz.common.util.page.PageResult;
import com.yz.mis.service.custom.GeneralConfigService;
import com.yz.mis.service.TResponseErrorEnumsService;
import com.yz.model.bo.mis.InsertResponseResultBO;
import com.yz.model.bo.mis.UpdateResponseResultBO;
import com.yz.model.dto.odr.OrderDTO;
import com.yz.model.entity.TOrder;
import com.yz.model.entity.TResponseErrorEnums;
import com.yz.service.base.service.BaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneralConfigServiceImpl extends BaseService implements GeneralConfigService {

    @Resource
    private RedisOperator redisOperator;
    @Resource
    private TResponseErrorEnumsService tResponseErrorEnumsService;

    @Override
    public PageResult<ResponseStatusEnum.ResponseStatusResult> searchResponseResult(
            String responseCode, String responseMsg,
            String language, Integer page,
            Integer pageSize) {
        List<ResponseStatusEnum.ResponseStatusResult> resultList = new ArrayList<>();
        // 1. 查询全部结果集
        List<ResponseStatusEnum.ResponseStatusResult> allResponseStatus
                = ResponseStatusEnum.getAllResponseStatus();
        // mis:response:
        String key = "";
        for (ResponseStatusEnum.ResponseStatusResult responseStatus : allResponseStatus) {
            ResponseStatusEnum.ResponseStatusResult result = new ResponseStatusEnum.ResponseStatusResult();
            String code = responseStatus.getCode();
            // mis:response:SUCCESS:es
            key = CacheKey.MIS + CacheKey.T_RESPONSE_ERROR_ENUMS + code + Strings.COLON + language;
            // 2. 查询缓存，命中则直接返回
            String msg = redisOperator.get(key);
            if (ObjectUtils.isNull(msg)) {
                // 3. 缓存没命中，查询数据库，并放入缓存
                TResponseErrorEnums tResponseErrorEnums =
                        tResponseErrorEnumsService.searchResponseByOne(code, language);
                if (ObjectUtils.isNotNull(tResponseErrorEnums)) {
                    redisOperator.set(key, tResponseErrorEnums.getMsg());
                    msg = tResponseErrorEnums.getMsg();
                }
            }
            // 4. 缓存和数据库只要任一命中则返回
            if (msg != null) {
                result.setCode(code);
                result.setMsg(msg);
                result.setStatus(responseStatus.getStatus());
                result.setSuccess(responseStatus.getSuccess());
                resultList.add(result);
            }
        }

        // 5. 根据 responseCode 查询条件精确匹配
        if (StrUtils.isNotBlank(responseCode)) {
            resultList = resultList
                    .stream()
                    .filter(p -> responseCode.equals(p.getCode()))
                    .collect(Collectors.toList());
        }
        // 6. 根据 responseMsg 查询条件模糊匹配
        if (StrUtils.isNotBlank(responseMsg)) {
            resultList = resultList
                    .stream()
                    .filter(p -> p.getMsg().contains(responseMsg))
                    .collect(Collectors.toList());
        }

        // 7. 针对返回的全部数据进行手动分页操作
        // 总记录数
        int size  = resultList.size();
        // 总页数
        long total = size/pageSize;
        if (size % pageSize != 0) {
            total = total + 1;
        }
        List<ResponseStatusEnum.ResponseStatusResult> resultListPage = new ArrayList<>();
        int startIndex = (page - 1) * pageSize;
        int endIndex = startIndex + pageSize;
        for (int i = startIndex; i < endIndex; i++) {
            if (i >= size) {
                break;
            }
            resultListPage.add(resultList.get(i));
        }
        Page<ResponseStatusEnum.ResponseStatusResult> pageInfo = new Page<>(page, pageSize, size);
        pageInfo.setPages(total);
        pageInfo.setRecords(resultListPage);
        return setPagePlus(pageInfo);
    }

    @Override
    public void insertResponseResult(InsertResponseResultBO bo) {
        List<String> languages = ListUtils.getLanguageList();
        for (String language : languages) {
            String msg = null;
            if (Strings.LOCALE_ES_LOWER.equals(language)) {
                msg = bo.getMsgByES();
            } else {
                msg = bo.getMsgByZH();
            }
            tResponseErrorEnumsService.insertResponseResult(bo.getCode(), bo.getStatus(), msg, language);
        }
    }

    @Override
    public void updateResponseResult(UpdateResponseResultBO bo) {
        tResponseErrorEnumsService.updateResponseResult(bo);
    }

    @Override
    public void deleteResponseResult(Long id) {
        tResponseErrorEnumsService.deleteResponseResult(id);
    }

}
