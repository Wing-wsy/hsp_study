package com.yz.mis.service;

import com.yz.model.bo.mis.UpdateResponseResultBO;
import com.yz.model.entity.TResponseErrorEnums;

/**
 * @author wing
 * @create 2025/1/22
 */
public interface TResponseErrorEnumsService {

    /**
     * 查询系统错误编码
     * @param code 编码
     * @param language 语言
     * @return
     */
    public TResponseErrorEnums searchResponseByOne(String code, String language);

    /**
     * 新增系统错误编码
     */
    public void insertResponseResult(String code, Integer status, String msg, String language);

    /**
     * 修改系统错误编码
     */
    public void updateResponseResult(UpdateResponseResultBO bo);

    /**
     * 删除系统错误编码
     */
    public void deleteResponseResult(Long id);


}
