package com.baizhi.test;

import com.baizhi.RabbitmqSpringbootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = RabbitmqSpringbootApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {


    //注入rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //简单模式
    @Test
    public void testHello(){
        rabbitTemplate.convertAndSend("hello","hello world");
    }

    //work
    @Test
    public void testWork(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work","work模型"+i);
        }
    }

    //fanout 广播
    @Test
    public void testFanout(){
        rabbitTemplate.convertAndSend("logs","","Fanout的模型发送的消息");
    }

    //route 路由模式
    @Test
    public void testRoute(){
        rabbitTemplate.convertAndSend("directs","info","发送info的key的路由信息");
//        rabbitTemplate.convertAndSend("directs","error","发送info的key的路由信息");
    }

    //topic 动态路由  订阅模式
    @Test
    public void testTopic(){
//        rabbitTemplate.convertAndSend("topics","user.save.add","produce.save.add 路由消息");
        rabbitTemplate.convertAndSend("topics","product.save.add","produce.save.add 路由消息");
    }








}
