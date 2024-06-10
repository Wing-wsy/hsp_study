package com.itheima.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该配置类会自动创建交换机和队列，以及交换机与队列的绑定关系，不用在页面操作
 * */
@Configuration
public class FanoutConfiguration {

    /**
     * 声明fanout交换机
     * */
    @Bean
    public FanoutExchange fanoutExchange(){
        // ExchangeBuilder.fanoutExchange("").build();  方式1
        return new FanoutExchange("hmall.fanout2");  // 方式2
    }

    /**
     * 声明队列
     * */
    @Bean
    public Queue fanoutQueue3(){
        // QueueBuilder.durable("ff").build(); durable：持久队列
        return new Queue("fanout.queue3");  // 效果和上面方式一样，默认持久
    }

    /**
     * 将队列与交换机绑定
     * */
    @Bean
    public Binding fanoutBinding3(Queue fanoutQueue3, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue3).to(fanoutExchange);
    }

    /**
     * 声明队列
     * */
    @Bean
    public Queue fanoutQueue4(){
        return new Queue("fanout.queue4");
    }

    /**
     * 将队列与交换机绑定（这个绑定和上面的效果是一样的）
     * */
    @Bean
    public Binding fanoutBinding4(){
        return BindingBuilder.bind(fanoutQueue4()).to(fanoutExchange());
    }
}
