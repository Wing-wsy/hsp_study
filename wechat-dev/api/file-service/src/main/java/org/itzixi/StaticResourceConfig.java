//package org.itzixi;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @Auther  这是本地保存图片才这样使用，用minio分布式文件服务就不用这个
// */
//@Configuration
//public class StaticResourceConfig extends WebMvcConfigurationSupport {
//
//    /**
//     * 添加静态资源映射路径，图片、视频、音频等都房子classpath下的static中
//     * @param registry
//     */
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        /**
//         * addResourceHandler: 指的是对外暴露的访问路径映射
//         * addResourceLocations: 指的本地文件所在的目录
//         */
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("file:/Users/wing/architect/github/project/hsp_study/temp");
//
//        // http://127.0.0.1:55/static/face/1772830312684302337.png
//
//        super.addResourceHandlers(registry);
//    }
//}
