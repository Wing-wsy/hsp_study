package com.yz.service.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yz.common.util.page.PageResult;

import java.util.List;

/**
 * 通用BaseService控制器
 */
public class BaseService {


    /**
     * 适用于 mybatis-plus
     * @param pageInfo
     * @return
     */
    public PageResult setPagePlus(Page<?> pageInfo) {

        //获取分页数据
        List<?> list = pageInfo.getRecords();
        // list.forEach(System.out::println);
//        System.out.println("当前页：" + pageInfo.getCurrent());
//        System.out.println("每页显示的条数：" + pageInfo.getSize());
//        System.out.println("总记录数：" + pageInfo.getTotal());
//        System.out.println("总页数：" + pageInfo.getPages());
//        System.out.println("是否有上一页：" + pageInfo.hasPrevious());
//        System.out.println("是否有下一页：" + pageInfo.hasNext());

        PageResult gridResult = new PageResult();
        gridResult.setRows(list);
        gridResult.setPage(pageInfo.getCurrent());
        gridResult.setRecords(pageInfo.getTotal());
        gridResult.setTotal(pageInfo.getPages());
        gridResult.setSize(pageInfo.getSize());
        return gridResult;
    }

    public <T,E> Page<E> convertPage(Page<T> page, List<E> list) {
        Page<E> pageInfoResult = new Page<>();
        pageInfoResult.setCurrent(page.getCurrent());
        pageInfoResult.setSize(page.getSize());
        pageInfoResult.setTotal(page.getTotal());
        pageInfoResult.setRecords(list);
        return pageInfoResult;
    }

    public void baseService() {
        System.out.println("baseService~~~~~");
    }
}
