package com.yz.service.base.controller;

import com.yz.common.exception.GraceException;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import com.yz.common.util.page.PageResult;

import java.util.List;

/**
 * 通用BaseController控制器
 */
public class BaseController {

    public <T,E> PageResult<E> convertPageResult(PageResult<T> page, List<E> list) {
        if (page.getPage() > page.getTotal()) {
            GraceException.display(ResponseStatusEnum.PAGE_ERROR_LIMIT);
        }
        PageResult<E> pageResult = new PageResult<>();
        pageResult.setPage(page.getPage());
        pageResult.setTotal(page.getTotal());
        pageResult.setSize(page.getSize());
        pageResult.setRecords(page.getRecords());
        pageResult.setRows(list);
        return pageResult;
    }

    public void baseController() {
        System.out.println("baseController~~~~~");
    }
}
