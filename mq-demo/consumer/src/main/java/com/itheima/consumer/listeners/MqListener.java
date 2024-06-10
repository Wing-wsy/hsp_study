package com.itheima.consumer.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class MqListener {

    // #################################### 简单模式 #############################################//
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String msg){
        System.out.println("消费者收到了simple.queue的消息：【" + msg +"】");
    }

    // #################################### 工作模式 #############################################//

    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1 收到了 work.queue的消息：【" + msg +"】");
        Thread.sleep(20);
    }


    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException {
        System.err.println("消费者2 收到了 work.queue的消息...... ：【" + msg +"】");
        Thread.sleep(200);
    }

    // #################################### 广播模式 #############################################//

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1 收到了 fanout.queue1的消息：【" + msg +"】");
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) throws InterruptedException {
        System.out.println("消费者2 收到了 fanout.queue2的消息：【" + msg +"】");
    }

    // #################################### 普通路由模式 ###########################################//

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "wing.queue1", durable = "true"),
            exchange = @Exchange(name = "wing.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1 收到了 wing.queue1的消息：【" + msg +"】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "wing.queue2", durable = "true"),
            exchange = @Exchange(name = "wing.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String msg) throws InterruptedException {
        System.out.println("消费者2 收到了 wing.queue2的消息：【" + msg +"】");
    }

    // #################################### 特殊路由模式 ###########################################//

    @RabbitListener(queues = "topic.queue1")
    public void listenTopicQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1 收到了 topic.queue1的消息：【" + msg +"】");
    }

    @RabbitListener(queues = "topic.queue2")
    public void listenTopicQueue2(String msg) throws InterruptedException {
        System.out.println("消费者2 收到了 topic.queue2的消息：【" + msg +"】");
    }

    @RabbitListener(queues = "object.queue")
    public void listenObject(Map<String, Object> msg) throws InterruptedException {
        System.out.println("消费者 收到了 object.queue的消息：【" + msg +"】");
    }
}
