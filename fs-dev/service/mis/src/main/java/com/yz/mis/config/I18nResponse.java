package com.yz.mis.config;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ResourceBundle;

/**
 * @author wing
 * @create 2025/1/20
 */
public class I18nResponse {
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public I18nResponse(String code, I18nEnum i18nEnum) {
        this.code = code;
        // 拿到配置文件设置语言
        final ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", LocaleContextHolder.getLocale());
        // 根据key值获取对应语言的数据
        this.msg = bundle.getString(i18nEnum.name());
    }

}
