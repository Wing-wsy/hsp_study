package com.itheima.publisher;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class PublisherApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class);
    }

    @Bean
    public MessageConverter jacksonMessageConvertor(){
        Jackson2JsonMessageConverter jjmc = new Jackson2JsonMessageConverter();
        //2.配置自动创建消息id，用于识别不同消息，也可以在业务中基于ID判断是否是重复消息【也可以不使用他的，可以自己使用雪花算法生成等方式】
        jjmc.setCreateMessageIds(true);
        return jjmc;
    }
}
