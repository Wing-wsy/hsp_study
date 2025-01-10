package com.yz.common.util;

import cn.hutool.core.util.RandomUtil;

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

}
