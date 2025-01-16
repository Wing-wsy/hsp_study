package com.yz.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * List工具类
 */
public class ListUtils {

    /**
     * 【功能描述】Map 转 List
     * @return
     */
    public static <T> List<T> mapToList(Object obj, Class<T> beanClass) {
        List<T> list = null;
        HashMap hashMap = (HashMap)obj;
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            list = (List<T>) entry.getValue();
            return list;
        }
        return list;
    }
}
