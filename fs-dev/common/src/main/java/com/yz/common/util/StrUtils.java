package com.yz.common.util;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 */
public class StrUtils {

    /**
     * 【功能描述】校验是否为空
     * 例：
     * StrUtils.isBlank(null) // true
     * StrUtils.isBlank("") // true
     * StrUtil.isBlank(" \t\n") // false
     * StrUtils.isBlank("abc") // false
     * 注意：如果不判断 " \t\n" ，建议使用isEmpty()，而不是isBlank()
     */
    public static boolean isEmpty(CharSequence st) {
        return StrUtil.isEmpty(st);
    }

    /**
     * 【功能描述】校验是否不为空
     *  StrUtils.isEmpty() 方法结果取反
     */
    public static boolean isNotEmpty(CharSequence st) {
        return StrUtil.isNotEmpty(st);
    }

    /**
     * 【功能描述】校验是否为空
     * 例：
     * StrUtils.isBlank(null) // true
     * StrUtils.isBlank("") // true
     * StrUtil.isBlank(" \t\n") // true
     * StrUtils.isBlank("abc") // false
     * 注意：该方法与 isEmpty(CharSequence) 的区别是： 该方法会校验空白字符，且性能相对于 isEmpty(CharSequence) 略慢。
     */
    public static boolean isBlank(CharSequence st) {
        return StrUtil.isBlank(st);
    }

    /**
     * 【功能描述】校验是否不为空
     *  StrUtils.isBlank() 方法结果取反
     */
    public static boolean isNotBlank(CharSequence st) {
        return StrUtil.isNotBlank(st);
    }

    /**
     * 【功能描述】指定字符串数组中，是否包含空字符串。
     * 如果指定的字符串数组的长度为 0，或者其中的任意一个元素是空字符串，则返回 true。
     *
     * 例：
     * StrUtil.hasBlank() // true
     * StrUtil.hasBlank("", null, " ") // true
     * StrUtil.hasBlank("123", " ") // true
     * StrUtil.hasBlank("123", "abc") // false
     * 使用场景：常用于判断好多字段是否有空的（例如web表单数据）
     */
    public static boolean hasBlank(CharSequence... strs) {
        return StrUtil.hasBlank(strs);
    }

    /**
     * 【功能描述】指定字符串数组中，是否包含空字符串。
     * 如果指定的字符串数组的长度为 0，或者其中的任意一个元素是空字符串，则返回 true。
     *
     * 例：
     * StrUtil.hasEmpty() // true
     * StrUtil.hasEmpty("", null) // true
     * StrUtil.hasEmpty("123", "") // true
     * StrUtil.hasEmpty("123", "abc") // false
     * 使用场景：常用于判断好多字段是否有空的（例如web表单数据）
     * 说明：hasEmpty只判断是否为null或者空字符串（""），hasBlank则会把不可见字符也算做空
     */
    public static boolean hasEmpty(CharSequence... strs) {
        return StrUtil.hasEmpty(strs);
    }

    /**
     * 【功能描述】去掉指定后缀
     * Params:
     * str – 字符串 suffix – 后缀
     * Returns:
     * 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(CharSequence str, CharSequence suffix) {
        return StrUtil.removeSuffix(str, suffix);
    }

    /**
     * 【功能描述】去掉指定前缀
     * Params:
     * str – 字符串 prefix – 前缀
     * Returns:
     * 切掉后的字符串，若前缀不是 preffix， 返回原字符串
     */
    public static String removePrefix(CharSequence str, CharSequence suffix) {
        return StrUtil.removePrefix(str, suffix);
    }

    /**
     * 【功能描述】去掉指定后缀（忽略大小写）
     * Params:
     * str – 字符串 suffix – 后缀
     * Returns:
     * 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffixIgnoreCase(CharSequence str, CharSequence suffix) {
        return StrUtil.removeSuffixIgnoreCase(str, suffix);
    }

    /**
     * 【功能描述】去掉指定前缀（忽略大小写）
     * Params:
     * str – 字符串 prefix – 前缀
     * Returns:
     * 切掉后的字符串，若前缀不是 preffix， 返回原字符串
     */
    public static String removePrefixIgnoreCase(CharSequence str, CharSequence suffix) {
        return StrUtil.removePrefixIgnoreCase(str, suffix);
    }


    /**
     * 【功能描述】改进JDK subString
     *  例：
     *  String str = "abcdefgh";
     *  String strSub1 = StrUtil.sub(str, 2, 3); //strSub1 -> c
     *  String strSub2 = StrUtil.sub(str, 2, -3); //strSub2 -> cde
     *  String strSub3 = StrUtil.sub(str, 3, 2); //strSub2 -> c
     *  Params:
     *  str – String fromIndexInclude – 开始的index（包括） toIndexExclude – 结束的index（不包括）
     *  Returns:
     *  字串
     */
    public static String sub(CharSequence str, int fromIndexInclude, int toIndexExclude) {
        return StrUtil.sub(str, fromIndexInclude, toIndexExclude);
    }

    /**
     * 【功能描述】格式化文本, {} 表示占位符 此方法只是简单将占位符 {} 按照顺序替换为参数
     *  String template = "{}爱{}，就像老鼠爱大米";
     *  String str = StrUtil.format(template, "我", "你"); //str -> 我爱你，就像老鼠爱大米
     *  Params:
     *  template – 文本模板，被替换的部分用 {} 表示，如果模板为null，返回"null" params – 参数值
     *  Returns:
     *  格式化后的文本，如果模板为null，返回"null"
     */
    public static String format(CharSequence template, Object... params) {
        return StrUtil.format(template, params);
    }


}
