package com.yz.service.base.config;

import com.yz.service.base.filter.HttpRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * filter配置类
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpRequestFilter());
        registration.addUrlPatterns("/*"); // 过滤全部(具体放行的URI，在过滤器内部判断)
        registration.setOrder(1);  // 需要越小优先级越高
        registration.setName("MyFilter");
        return registration;
    }
}
