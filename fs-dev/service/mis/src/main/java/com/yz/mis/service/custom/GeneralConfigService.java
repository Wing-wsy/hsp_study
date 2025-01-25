package com.yz.mis.service.custom;

import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.page.PageResult;
import com.yz.model.bo.mis.InsertResponseResultBO;
import com.yz.model.bo.mis.UpdateResponseResultBO;


public interface GeneralConfigService {

    /**
     * 查询系统响应结果类型
     * @param language 语言编码
     */
    public PageResult<ResponseStatusEnum.ResponseStatusResult> searchResponseResult(
            String responseCode, String responseMsg,
            String language, Integer page,
            Integer pageSize);

    /**
     * 新增系统响应结果类型
     * @param bo
     */
    public void insertResponseResult(InsertResponseResultBO bo);

    /**
     * 修改系统响应结果类型
     * @param bo
     */
    public void updateResponseResult(UpdateResponseResultBO bo);

    /**
     * 删除系统响应结果类型
     * @param id
     */
    public void deleteResponseResult(Long id);



}
