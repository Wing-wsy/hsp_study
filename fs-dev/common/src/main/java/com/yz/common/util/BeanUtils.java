package com.yz.common.util;


import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean工具类
 */
public class BeanUtils {
    /**
     *
     * @param list 转换前原始数据
     * @param clazz 转换后的bean类型
     * @return
     */
    public static <T,E> List<T> convertBeanList(List<E> list, Class<T> clazz) {
        List<T> tList = new ArrayList<>();
        for (E e : list) {
            T t = null;
            try {
                t = clazz.newInstance();
            } catch (Exception ex) {
                return null;
            }
            BeanUtil.copyProperties(e, t);
            tList.add(t);
        }
        return tList;
    }

    /**
     * 【功能描述】对象或Map转Bean
     * Params:
     * source – Bean对象或Map clazz – 目标的Bean类型 options – 属性拷贝选项
     * Returns:
     * Bean对象
     */
    public static <T> T toBean(Object source, Class<T> clazz) {
        return BeanUtil.toBean(source, clazz, null);
    }



}
