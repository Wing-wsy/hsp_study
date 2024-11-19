package org.itzixi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableDiscoveryClient  // 开启服务的注册和发现功能
@MapperScan(basePackages = "org.itzixi.mapper")
@EnableFeignClients("org.itzixi.api.feign")
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
