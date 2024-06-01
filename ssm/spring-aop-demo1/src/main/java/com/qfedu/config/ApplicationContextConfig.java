package com.qfedu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author wing
 * @create 2024/6/1
 */
@Configuration
//@ComponentScan({"com.qfedu.dao"})
@ComponentScan({"com.qfedu.service"})
@Import(OtherConfig.class)
public class ApplicationContextConfig {}
