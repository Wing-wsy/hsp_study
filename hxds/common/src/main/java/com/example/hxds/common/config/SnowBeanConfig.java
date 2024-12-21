package com.example.hxds.common.config;

import com.example.hxds.common.util.SnowflakeIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 雪花算法
 */
@Configuration
public class SnowBeanConfig {
    @Bean
    public SnowflakeIdWorker getIdWorker() {
        return new SnowflakeIdWorker(2, 3);
    }
}
