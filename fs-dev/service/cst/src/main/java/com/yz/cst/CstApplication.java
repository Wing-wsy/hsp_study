package com.yz.cst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("com.yz")
public class CstApplication {

    private static final Logger LOG = LoggerFactory.getLogger(CstApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CstApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info(
                "测试地址: \thttp://127.0.0.1:{}{}/h/hello",
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path")
        );
        LOG.info(
                "文档地址: \thttp://127.0.0.1:{}{}/swagger-ui/index.html?configUrl=/doc-api.html",
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path")
        );
    }

}
