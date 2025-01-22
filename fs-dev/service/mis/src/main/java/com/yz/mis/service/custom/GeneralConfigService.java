package com.yz.mis.service.custom;

import com.yz.common.result.ResponseStatusEnum;

import java.util.List;

public interface GeneralConfigService {

    /**
     * 查询系统响应结果类型
     * @param language 语言编码
     */
    public List<ResponseStatusEnum.ResponseStatusResult> searchResponseResult(String language);


}
