package com.yz.cst;

import com.yz.common.util.StrUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableDiscoveryClient  // 开启服务的注册和发现功能
@ComponentScan("com.yz")
@MapperScan(basePackages = "com.yz.cst.mapper")
public class CstApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CstApplication.class);
        Environment env = app.run(args).getEnvironment();
        String ip = "127.0.0.1";
        String port = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        System.out.println(StrUtils.format("启动成功！！( ^_^ )"));
        System.out.println(StrUtils.format("测试地址: \thttp://{}:{}{}/h/hello",ip, port, contextPath));
        System.out.println(StrUtils.format("文档地址: \thttp://{}:{}{}/swagger-ui/index.html?configUrl=/doc-api.html",ip, port, contextPath));
    }

}
