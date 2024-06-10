package com.baizhi.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue(value = "hello")) // 默认创建持久化，非独占
public class HelloCustomer {

    @RabbitHandler
    public void receive1(String message){
        System.out.println("message = " + message);
    }


}
