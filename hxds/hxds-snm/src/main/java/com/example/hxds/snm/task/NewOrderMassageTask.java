package com.example.hxds.snm.task;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.example.hxds.common.exception.HxdsException;
import com.example.hxds.snm.entity.NewOrderMessage;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class NewOrderMassageTask {
    @Resource
    private ConnectionFactory factory;

    /**
     * 同步发送
     * @param list
     */
    public void sendNewOrderMessage(ArrayList<NewOrderMessage> list) {
        // 过期时间一分钟
        int ttl = 1 * 60 * 1000;
        String exchangeName = "new_order_private";
        // 博客【java中try()的妙用】：https://blog.csdn.net/dxjren/article/details/127674943
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            HashMap param = new HashMap();
            for (NewOrderMessage message : list) {
                HashMap map = new HashMap() {{
                    put("orderId", message.getOrderId());
                    put("from", message.getFrom());
                    put("to", message.getTo());
                    put("expectsFee", message.getExpectsFee());
                    put("mileage", message.getMileage());
                    put("minute", message.getMinute());
                    put("distance", message.getDistance());
                    put("favourFee", message.getFavourFee());
                }};
                AMQP.BasicProperties properties = new AMQP.BasicProperties()
                        .builder().contentEncoding("UTF-8").headers(map)
                        .expiration(ttl + "").build();

                // message.getUserId() 拿出来的是司机ID
                String queueName = "queue_" + message.getUserId();
                String routingKey = message.getUserId();
                channel.queueDeclare(queueName, true, false, false, param);
                channel.queueBind(queueName, exchangeName, routingKey);
                channel.basicPublish(exchangeName, routingKey, properties, ("新订单" + message.getOrderId()).getBytes());
                log.debug(message.getUserId() + "的新订单消息发送成功" + Thread.currentThread().getName());

            }
        } catch (Exception e) {
            log.error("执行异常", e);
            throw new HxdsException("新订单消息发送失败");
        }
    }

    /**
     * 异步发送
     * @param list
     */
    @Async("AsyncTaskExecutor")
    public void sendNewOrderMessageAsync(ArrayList<NewOrderMessage> list) {
        this.sendNewOrderMessage(list);
    }

    public List<NewOrderMessage> receiveNewOrderMessage(long userId) {
        String exchangeName = "new_order_private"; //交换机名字
        String queueName = "queue_" + userId; //队列名字
        String routingKey = userId + ""; //routing key
        List<NewOrderMessage> list = new ArrayList<>();
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
            // 取10条消息
            channel.basicQos(0, 10, true);
            while (true) {
                GetResponse response = channel.basicGet(queueName, false);
                if (response != null) {
                    AMQP.BasicProperties properties = response.getProps();
                    Map<String, Object> map = properties.getHeaders();
                    String orderId = MapUtil.getStr(map, "orderId");
                    String from = MapUtil.getStr(map, "from");
                    String to = MapUtil.getStr(map, "to");
                    String expectsFee = MapUtil.getStr(map, "expectsFee");
                    String mileage = MapUtil.getStr(map, "mileage");
                    String minute = MapUtil.getStr(map, "minute");
                    String distance = MapUtil.getStr(map, "distance");
                    String favourFee = MapUtil.getStr(map, "favourFee");

                    NewOrderMessage message = new NewOrderMessage();
                    message.setOrderId(orderId);
                    message.setFrom(from);
                    message.setTo(to);
                    message.setExpectsFee(expectsFee);
                    message.setMileage(mileage);
                    message.setMinute(minute);
                    message.setDistance(distance);
                    message.setFavourFee(favourFee);

                    list.add(message);

                    byte[] body = response.getBody();
                    String msg = new String(body);
                    log.debug("从RabbitMQ接收的订单消息：" + msg);

                    long deliveryTag = response.getEnvelope().getDeliveryTag();
                    channel.basicAck(deliveryTag, false);

                } else {
                    break;
                }
            }
            // 将list的顺序倒过来（最新的消息在前面）
            ListUtil.reverse(list);
            return list;
        } catch (Exception e) {
            log.error("执行异常", e);
            throw new HxdsException("接收新订单失败");
        }

    }

    public void deleteNewOrderQueue(long userId) {
        String exchangeName = "new_order_private"; //交换机名字
        String queueName = "queue_" + userId; //队列名字
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.exchangeDeclare(exchangeName,BuiltinExchangeType.DIRECT);
            channel.queueDelete(queueName);
            log.debug(userId + "的新订单消息队列成功删除");
        } catch (Exception e) {
            log.error(userId + "的新订单队列删除失败", e);
            throw new HxdsException("新订单队列删除失败");
        }
    }

    @Async("AsyncTaskExecutor")
    public void deleteNewOrderQueueAsync(long userId){
        this.deleteNewOrderQueue(userId);
    }

    public void clearNewOrderQueue(long userId){
        String exchangeName = "new_order_private"; //交换机名字
        String queueName = "queue_" + userId; //队列名字
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.exchangeDeclare(exchangeName,BuiltinExchangeType.DIRECT);
            channel.queuePurge(queueName);
            log.debug(userId + "的新订单消息队列清空删除");
        } catch (Exception e) {
            log.error(userId + "的新订单队列清空失败", e);
            throw new HxdsException("新订单队列清空失败");
        }
    }

    @Async("AsyncTaskExecutor")
    public void clearNewOrderQueueAsync(long userId){
        this.clearNewOrderQueue(userId);
    }
}
