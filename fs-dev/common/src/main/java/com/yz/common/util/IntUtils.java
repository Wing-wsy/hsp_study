package com.yz.common.util;


/**
 * Integer工具类
 */
public class IntUtils {

    public static Integer parseInt(String st) {
        if (StrUtils.isBlank(st))
            return null;

        return Integer.parseInt(st);
    }
}
