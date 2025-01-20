package com.yz.service.base.util;

import com.yz.common.result.ResponseStatusEnum;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ResourceBundle;

public class MessageI18nUtils {

    /**
     * 获取单个国际化翻译值
     */
    public static String get(ResponseStatusEnum responseStatusEnum) {
        // 拿到配置文件设置语言
        final ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", LocaleContextHolder.getLocale());
        // 根据key值获取对应语言的数据
        return bundle.getString(responseStatusEnum.name());
    }
}
