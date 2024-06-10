package com.itheima.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 定义专门保存消费者消费消息一直失败，达到重试阈值的消息
 * */
@Slf4j
@Configuration
// 当配置文件配了这个，并且开启了需要重试，这个类才加载，否则不加载
@ConditionalOnProperty(name = "spring.rabbitmq.listener.simple.retry.enabled", havingValue = "true")
public class ErrorConfiguration {

    /**
     * 定义交换机
     */
    @Bean
    public DirectExchange errorExchange(){
        return new DirectExchange("error.direct");
    }

    /**
     * 定义队列
     */
    @Bean
    public Queue errorQueue(){
        return new Queue("error.queue");
    }

    /**
     * 交换机与队列绑定（路由：error）
     */
    @Bean
    public Binding errorBinding(Queue errorQueue, DirectExchange errorExchange){
        return BindingBuilder.bind(errorQueue).to(errorExchange).with("error");
    }

    /**
     * `RepublishMessageRecoverer`：重试耗尽后，将失败消息投递到指定的交换机
     */
    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate){
        log.debug("加载RepublishMessageRecoverer");
        return new RepublishMessageRecoverer(rabbitTemplate, "error.direct", "error");
    }
}
