package com.yz.mis.service;

import com.yz.common.util.page.PageResult;
import com.yz.model.bo.mis.AddDeptBO;
import com.yz.model.vo.mis.SelectDeptListVO;


public interface TSystemDeptService {

    /**
     * 查询部门
     */
    public PageResult<SelectDeptListVO> selectDeptList(String language, Integer status,
                                                       Integer page, Integer pageSize);

    /**
     * 新增部门
     */
    public void addDept(AddDeptBO bo);

    /**
     * 删除部门
     */
    public void deleteDept(Long id);


}
