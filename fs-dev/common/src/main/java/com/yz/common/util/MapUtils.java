package com.yz.common.util;


import cn.hutool.core.map.MapUtil;

import java.util.Map;

/**
 * Map工具类
 */
public class MapUtils {

    /**
     * 获取Map指定key的值，并转换为Long
     *
     * @param map Map
     * @param key 键
     */
    public static Long getLong(Map<?, ?> map, Object key) {
        return MapUtil.get(map, key, Long.class);
    }


}
