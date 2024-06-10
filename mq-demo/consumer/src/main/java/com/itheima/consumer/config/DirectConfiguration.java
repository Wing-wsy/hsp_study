package com.itheima.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该配置类会自动创建交换机和队列，以及交换机与队列的绑定关系，不用在页面操作
 * 缺点：路由太多，绑定起来太麻烦，推荐使用注解方式【参考 listenDirectQueue1 方法】
 * */
//@Configuration
public class DirectConfiguration {

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("wing.direct");
    }

    @Bean
    public Queue directQueue1(){
        return new Queue("wing.queue1");
    }

    @Bean
    public Binding directQueue1BindingRed(Queue directQueue1, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue1).to(directExchange).with("red");
    }

    @Bean
    public Binding directQueue1BindingBlue(Queue directQueue1, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue1).to(directExchange).with("blue");
    }

    @Bean
    public Queue directQueue2(){
        return new Queue("wing.queue2");
    }

    @Bean
    public Binding directQueue2BindingRed(Queue directQueue2, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue2).to(directExchange).with("red");
    }

    @Bean
    public Binding directQueue2BindingBlue(Queue directQueue2, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue2).to(directExchange).with("yellow");
    }

}
