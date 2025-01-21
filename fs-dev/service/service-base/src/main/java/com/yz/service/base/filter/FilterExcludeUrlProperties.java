package com.yz.service.base.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义过滤器排除URI
 */
@Component
@Data
@PropertySource("classpath:filterExcludeUrlPath.properties")
@ConfigurationProperties(prefix = "filter")
public class FilterExcludeUrlProperties {

    // 需要过滤的URL
    private List<String> urls;

    // 排除过滤的URL
    private List<String> excludeUrls;

}
