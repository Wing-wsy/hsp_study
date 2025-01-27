package com.yz.common.util;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;

/**
 * 集合工具类
 */
public class CollUtils {

    /**
     * 集合是否为空
     * Params:
     * collection – 集合
     * Returns:
     * 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollUtil.isEmpty(collection);
    }

    /**
     * 集合是否为非空
     * Params:
     * collection – 集合
     * Returns:
     * 是否为空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return CollUtil.isNotEmpty(collection);
    }

}
