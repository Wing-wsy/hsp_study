package com.yz.common.util;

import cn.hutool.core.util.RandomUtil;
import org.slf4j.MDC;

/**
 * 随机字符串工具类
 */
public class RandomUtils {

    /**
     * 【功能描述】获得一个随机的字符串（只包含数字和字符）
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        return RandomUtil.randomString(length);
    }

    /**
     * 【功能描述】获得一个只包含数字的字符串
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomNumbers(int length) {
        return RandomUtil.randomNumbers(length);
    }

    /**
     * 【功能描述】生成 TraceId 字符串
     * @param length 拼接随机字符串的长度
     * @return 当前毫秒数 + 指定长度随机字符串
     */
    public static String createTraceId(int length) {
        return System.currentTimeMillis() + randomString(length);
    }
}
