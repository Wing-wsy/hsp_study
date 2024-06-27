/*
package com.blr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//自定义 mvc 配置类 【处理跨域方式四】
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    //用来全局处理跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //对那些请求进行跨域处理
                .allowCredentials(false)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .maxAge(3600);

    }
}
*/
