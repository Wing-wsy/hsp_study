//package com.yz.mis.config;
//
//import com.yz.mis.filter.HttpRequestFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author wing
// * @create 2025/1/20
// */
//@Configuration
//
//public class FilterConfig {
//    @Bean
//    public FilterRegistrationBean simpleCORSFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new HttpRequestFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("MyFilter");
//        registration.setOrder(1);
//        return registration;
//    }
//}
