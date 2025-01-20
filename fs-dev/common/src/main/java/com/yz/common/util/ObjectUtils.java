package com.yz.common.util;

import cn.hutool.core.util.ObjectUtil;


/**
 * Object工具类
 */
public class ObjectUtils {

    /**
     * 检查对象是否为null 判断标准为：
     *   1. == null
     *   2. equals(null)
     *
     * Params:
     * obj – 对象
     * Returns:
     * 是否为null
     */
    public static boolean isNull(Object obj) {
        return ObjectUtil.isNull(obj);
    }

    /**
     * 检查对象是否不为null
     */
    public static boolean isNotNull(Object obj) {
        return false == ObjectUtils.isNull(obj);
    }
}
