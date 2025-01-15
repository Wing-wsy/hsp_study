package com.yz.base.bff.config;

import com.yz.common.constant.Strings;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        requestTemplate.header(Strings.TRACE_ID, MDC.get(Strings.TRACE_ID));

    }
}