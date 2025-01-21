package com.yz.mis.controller;

import com.yz.common.result.GraceResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 */
@RestController
public class I18nTestController {

    @PostMapping("/test/i18n")
    public GraceResult testI18n(@RequestBody @Valid I18nBO bo) {

        // 这里是就是执行完逻辑给前端的提示
        return GraceResult.ok();

    }

}
