package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.web.DefaultSecurityFilterChain;

@SpringBootApplication
public class SpringSecurity01Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringSecurity01Application.class, args);
        // 该bean中的filters成员变量保存着15个过滤器（Array集合有顺序）
        DefaultSecurityFilterChain bean = run.getBean(DefaultSecurityFilterChain.class);
    }

}
