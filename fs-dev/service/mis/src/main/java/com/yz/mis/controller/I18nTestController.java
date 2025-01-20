package com.yz.mis.controller;

import com.yz.common.result.ResponseStatusEnum;
import com.yz.mis.config.I18nEnum;
import com.yz.mis.config.I18nResponse;
import com.yz.service.base.util.MessageI18nUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

/**
 * @author wing
 * @create 2025/1/20
 */
@RestController
public class I18nTestController {

    @Resource
    private LocaleResolver localeResolver;

    @PostMapping("/test/i18n")
    public I18nResponse testI18n(HttpServletRequest request, HttpServletResponse response,
                                 @RequestBody I18nBO bo) {
        String language = bo.getLanguage();
        Locale locale = null;
        if (language.equals("en")) {
            locale = new Locale("en","US");
        }
        if (language.equals("zh")) {
            locale = new Locale("zh","CN");
        }

        if (locale != null) {
            localeResolver.setLocale(request, response, locale);
        }

        String s = MessageI18nUtils.get(ResponseStatusEnum.SYSTEM_ERROR_GLOBAL);
        System.out.println(s);


        // 这里是就是执行完逻辑给前端的提示
        return new I18nResponse("0001", I18nEnum.USER_NAME_ISNOT_NULL);

    }

}
