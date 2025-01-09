package com.yz.common.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * JSON工具类
 */
public class JSONUtils {

    /**
     * 【功能描述】创建JSONObject
     * @return
     */
    public static JSONObject createJSONObject() {
        return JSONUtil.createObj();
    }
    /**
     * 【功能描述】创建JSONArray
     * @return
     */
    public static JSONArray createJSONArray() {
        return JSONUtil.createArray();
    }

    /**
     * 【功能描述】Obj 转成 json字符串
     * @param obj 需要转成json字符串的对象
     *        支持：普通对象、Map、List、
     * @return
     */
    public static String toJsonStr(Object obj) {
        return JSONUtil.toJsonStr(obj);
    }

    /**
     * 【功能描述】json字符串 转成 Obj
     * @param jsonString – JSON字符串
     *        beanClass – 实体类对象
     * @return 实体类对象
     */
    public static <T> T toBean(String jsonString, Class<T> beanClass) {
        return JSONUtil.toBean(jsonString, beanClass);
    }

    /**
     * 【功能描述】JSON字符串转JSONObject对象
     * @param obj – Bean对象或者Map
     * @return JSONObject
     */
    public static JSONObject parseObj(Object obj) {
        return JSONUtil.parseObj(obj);
    }

    /**
     * 【功能描述】JSON字符串转JSONArray
     * @param arrayOrCollection – 数组或集合对象
     * @return JSONArray
     */
    public static JSONArray parseArray(Object arrayOrCollection) {
        return JSONUtil.parseArray(arrayOrCollection);
    }

}
