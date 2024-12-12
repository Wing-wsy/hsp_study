> RabbitMQ详细：https://blog.csdn.net/weixin_42039228/article/details/123493937?spm=1001.2014.3001.5506
>
> RabbitMQ之批量消费：https://blog.csdn.net/CSDN_LiMingfly/article/details/138372320
>
> RabbitMQ之消费者确认机制：https://blog.csdn.net/qushaming/article/details/142857916
>
> RabbitMQ之失败消息处理策略：https://blog.csdn.net/qushaming/article/details/142861075

# 1 消息中间件

消息中间件是基于队列与[消息传递](https://so.csdn.net/so/search?q=消息传递&spm=1001.2101.3001.7020)技术，在网络环境中为应用系统提供同步或异步、可靠的消息传输的支撑性软件系统

## 1.1 应用场景

场景说明：

> 用户注册后，需要发注册邮件和注册短信，传统的做法有两种
>
> - 1.串行的方式；
> - 2.并行的方式 ；

(1)串行方式：将注册信息写入数据库后，发送注册邮件，再发送注册短信，以上三个任务全部完成后才返回给客户端。这有一个问题是，邮件，短信并不是必须的，它只是一个通知，而这种做法让客户端等待没有必要等待的东西。

![](https://i-blog.csdnimg.cn/blog_migrate/bc700797801c69b11a13e2ed0dc4ab74.png)

(2)并行方式：将注册信息写入数据库后，发送邮件的同时，发送短信，以上三个任务完成后，返回给客户端，并行的方式能提高处理的时间。

![](https://i-blog.csdnimg.cn/blog_migrate/6b0df64b2eec2f61f96bc15b30b63584.png)

假设三个业务节点分别使用50ms，串行方式使用时间150ms，并行使用时间100ms。虽然并性已经提高的处理时间，但是，前面说过邮件和短信对我正常的使用网站没有任何影响，客户端没有必要等着其发送完成才显示注册成功，应该是写入数据库后就返回。

(3)消息队列
引入消息队列后，把发送邮件，短信不是必须的业务逻辑异步处理

![](https://i-blog.csdnimg.cn/blog_migrate/4f0aef82cd324e467793fa2efcf18d7e.png)

由此可以看出，引入消息队列后，用户的响应时间就等于写入数据库的时间+写入消息队列的时间(可以忽略不计)，引入消息队列后处理后，响应时间是串行的3倍，是并行的2倍。

***

## 1.2 应用解耦

场景：

> 双11是购物狂节，用户下单后，订单系统需要通知库存系统，传统的做法就是订单系统调用库存系统的接口。

![](https://i-blog.csdnimg.cn/blog_migrate/f2a5cfe5d5b240ef0291e4d41923b240.png)

这种做法有一个缺点:

- 当库存系统出现故障时，订单就会失败。
- 订单系统和库存系统高耦合。

**引入消息队列**

![](https://i-blog.csdnimg.cn/blog_migrate/d31d312f4aae8dc1be9ab51642b03958.png)

订单系统：用户下单后，订单系统完成持久化处理，将消息写入消息队列，返回用户订单下单成功。

库存系统：订阅下单的消息，获取下单消息，进行库操作。
就算库存系统出现故障,消息队列也能保证消息的可靠投递,不会导致消息丢失。

***

## 1.3 流量削峰

流量削峰一般在秒杀活动中应用广泛

场景:

> 秒杀活动，一般会因为流量过大，导致应用挂掉，为了解决这个问题，一般在应用前端加入消息队列。

作用:
1、可以控制活动人数，超过此一定阀值的订单直接丢弃(我为什么秒杀一次都没有成功过呢^^)
2、可以缓解短时间的高流量压垮应用(应用程序按自己的最大处理能力获取订单)

![](https://i-blog.csdnimg.cn/blog_migrate/655af2fb5e82446d6491904cd9828b9e.png)

1、用户的请求，服务器收到之后，首先写入消息队列，加入消息队列长度超过最大值，则直接抛弃用户请求或跳转到错误页面。

2、秒杀业务根据消息队列中的请求信息，再做后续处理。

***

## 1.4 消息队列优缺点

关于消息队列的优点也就是上面列举的，就是在特殊场景下有其对应的好处，解耦、异步、削峰。

缺点有以下几个：

- **系统可用性降低**

  系统引入的外部依赖越多，越容易挂掉。本来你就是 A 系统调用 BCD 三个系统的接口就好了，人 ABCD 四个系统好好的，没啥问题，你偏加个 MQ 进来，万一 MQ 挂了咋整，MQ 一挂，整套系统崩溃的，你不就完了？如何保证消息队列的高可用

- **系统复杂度提高**
  硬生生加个 MQ 进来，你怎么[保证消息没有重复消费]？怎么[处理消息丢失的情况]？怎么保证消息传递的顺序性？头大头大，问题一大堆，痛苦不已。
- **一致性问题**
  A 系统处理完了直接返回成功了，人都以为你这个请求就成功了；但是问题是，要是 BCD 三个系统那里，BD 两个系统写库成功了，结果 C 系统写库失败了，咋整？你这数据就不一致了。

> 所以消息队列实际是一种非常复杂的架构，你引入它有很多好处，但是也得针对它带来的坏处做各种额外的技术方案和架构来规避掉，做好之后，你会发现，妈呀，系统复杂度提升了一个数量级，也许是复杂了 10 倍。但是关键时刻，用，还是得用的。

# 2 常用消息中间件

AMQP，即Advanced Message Queuing Protocol，一个提供统一消息服务的应用层标准高级消息队列协议，是应用层协议的一个开放标准，为面向消息的中间件设计。基于此协议的客户端与消息中间件可传递消息，并不受客户端/中间件不同产品，不同的开发语言等条件的限制。Erlang中的实现有RabbitMQ等。

JMS即Java消息服务（Java Message Service）应用程序接口，是一个Java平台中关于面向消息中间件（MOM）的API，用于在两个应用程序之间，或分布式系统中发送消息，进行异步通信。Java消息服务是一个与具体平台无关的API，绝大多数MOM提供商都对JMS提供支持。

**AMQP和JMS**

`MQ`是消息通信的模型，并发具体实现。现在实现`MQ`的有两种主流方式：`AMQP、JMS`。

两者间的区别和联系：

- `JMS`是定义了统一的接口，来对消息操作进行统一；`AMQP`是通过规定协议来统一数据交互的格式
- `JMS`限定了必须使用`Java`语言；`AMQP`只是协议，不规定实现方式，因此是跨语言的。
- `JMS`规定了两种消息模型；而`AMQP`的消息模型更加丰富

**常见MQ产品**

`ActiveMQ`：基于`JMS`
`RabbitMQ`：基于`AMQP`协议，`erlang`语言开发，稳定性好
`RocketMQ``：基于JMS`，阿里巴巴产品，目前交由`Apache`基金会
`Kafka`：分布式消息系统，高吞吐量

其实现在主流的消息中间件就4种：`kafka、ActiveMQ、RocketMQ、RabbitMQ`

下面我们来看一下，他们之间有什么区别，他们分别应该用于什么场景

## 2.1 ActiveMQ

我们先看`ActiveMQ`。其实一般早些的项目需要引入消息中间件，都是使用的这个`MQ`，但是现在用的确实不多了，说白了就是有些过时了。我们去它的官网看一看，你会发现官网已经不活跃了，好久才会更新一次。

它的单机吞吐量是万级，一些小的项目已经够用了，但对于高并发的互联网项目完全不够看。

在高可用上，使用的主从架构的实现。
在消息可靠性上，有较低的概率会丢失数据。
综合以上，其实这个产品基本可以弃用掉了，我们完全可以使用RabbitMQ来代替它。

## 2.2 RabbitMQ

`RabbitMQ`出现后，国内大部分公司都从`ActiveMQ`切换到了`RabbitMQ`，基本代替了`activeMQ`的位置。它的社区还是很活跃的。

它的单机吞吐量也是万级，对于需要支持特别高的并发的情况，它是无法担当重任的。

在高可用上，它使用的是镜像集群模式，可以保证高可用。
在消息可靠性上，它是可以保证数据不丢失的，这也是它的一大优点。

同时它也支持一些消息中间件的高级功能，如：消息重试、死信队列等。

但是，它的开发语言是`erlang`，国内很少有人精通`erlang`，所以导致无法阅读源码。
对于大多数中小型公司，不需要面对技术上挑战的情况，使用它还是比较合适的。而对于一些`BAT`大型互联网公司，显然它就不合适了。

## 2.3 RocketMQ

接下来我们来讨论一下我比较喜欢的`MQ-RocketMQ`，它是阿里[开源](https://edu.csdn.net/cloud/pm_summit?utm_source=blogglc&spm=1001.2101.3001.7020)的消息中间件，久经沙场，非常靠谱。

它支持高吞吐量，能达到10万级，能承受互联网项目高并发的挑战。

在高可用上，它使用的是分布式架构，可以搭建大规模集群，性能很高。

在消息可靠性上，通过配置，可以保证数据的绝对不丢失。
同时它支持大量的高级功能，如：延迟消息、事务消息、消息回溯、死信队列等等。

它非常适合应用于java系统架构中，因为它使用java语言开发的，我们可以去阅读源码了解更深的底层原理。

目前来看，它没有什么特别的缺点，可以支持高并发下的技术挑战，可以基于它实现分布式事务，大型互联网公司和中小型公司都可以选择使用它来作为消息中间件使用，如果我来做技术选型，我首选的中间件就是它。

## 2.4 Kafka

kafka的吞吐量被公认为中间件中的翘楚，单机可以支持十几万的并发，相当强悍。

在高可用上同样支持分布式集群部署。

在消息可靠性上，如果保证异步的性能，可能会出现消息丢失的情况，因为它保存消息时是先存到磁盘缓冲区的，如果机器出现故障，缓冲区的数据是可能丢失的。

它的功能非常的单一，就是消息的接收与发送，因此不适合应用于许多场景。

它在行业内主要应用于大数据领域，使用它进行用户行为日志的采集和计算，来实现比如“猜你喜欢”的功能。

所以，如果没有大数据的需求，一般不会选择它。

***

## 2.5 为什么选择RabbitMQ

1、`ActiveMQ`，性能不是很好，因此在高并发的场景下，直接被`pass`掉了。它的`Api`很完善，在中小型互联网公司可以去使用。

2、`kafka`，主要强调高性能，如果对业务需要可靠性消息的投递的时候。那么就不能够选择`kafka`了。但是如果做一些日志收集呢，`kafka`还是很好的。因为`kafka`的性能是十分好的。

3、`RocketMQ`，它的特点非常好。它高性能、满足可靠性、分布式事物、支持水平扩展、上亿级别的消息堆积、主从之间的切换等等。`MQ`的所有优点它基本都满足。但是它最大的缺点：商业版收费。因此它有许多功能是不对外提供的。

## 2.6 比较分析图

![](https://i-blog.csdnimg.cn/blog_migrate/6d602b7028662f1ddba911b6efb758d5.png#pic_center)

***

# 3 RabbitMQ介绍

`RabbitMQ`是由`erlang`语言开发，基于`AMQP`（Advanced Message Queue 高级消息队列协议）协议实现的消息队列，它是一种应用程序之间的通信方法，消息队列在分布式系统开发中应用非常广泛。

## 3.1 特点

`RabbitMQ`是使用`Erlang`语言开发的开源消息队列系统，基于`AMQP`协议来实现。
`AMQP`的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。

`AMQP`协议更多用在企业系统内，对数据一致性、稳定性和可靠性要求很高的场景，对性能和吞吐量的要求还在其次。

`RabbitMQ`的可靠性是非常好的，数据能够保证百分之百的不丢失。可以使用镜像队列，它的稳定性非常好。所以说在我们互联网的金融行业。对数据的稳定性和可靠性要求都非常高的情况下，我们都会选择`RabbitMQ`。当然没有`kafka`性能好，但是要比`AvtiveMQ`性能要好很多。也可以自己做一些性能的优化。

`RabbitMQ`可以构建异地双活架构，包括每一个节点存储方式可以采用磁盘或者内存的方式。

## 3.2 RabbitMQ的集群架构

![](https://i-blog.csdnimg.cn/blog_migrate/97ef8cd39bf744fca7bd5618c9939e49.png#pic_center)

非常经典的 `mirror `**镜像模式**，保证 `100% `数据不丢失。在实际工作中也是用得最多的，并且实现非常的简单，一般互联网大厂都会构建这种镜像集群模式。

`mirror `镜像队列，目的是为了保证 `rabbitMQ `数据的高可靠性解决方案，主要就是实现数据的同步，一般来讲是`2 - 3`个节点实现数据同步。对于`100%`数据可靠性解决方案，一般是采用 `3 `个节点。

如上图所示，用 `KeepAlived` 做了 `HA-Proxy` 的高可用，然后有` 3` 个节点的 `MQ` 服务，消息发送到主节点上，主节点通过 `mirror `队列把数据同步到其他的`MQ`节点，这样来实现其高可靠。

这就是`RabbitMQ`整个镜像模式的集群架构。

***

# 4 RabbitMQ的工作原理介绍

首先先介绍一个简单的一个消息推送到接收的流程，提供一个简单的图：

![](https://i-blog.csdnimg.cn/blog_migrate/26c9666bf2c35784fb22e8198509481c.png)

黄色的圈圈就是我们的消息推送服务，将消息推送到 中间方框里面也就是 `rabbitMq`的服务器，然后经过服务器里面的交换机、队列等各种关系将数据处理入列后，最终右边的蓝色圈圈消费者获取对应监听的消息。

下图是`RabbitMQ`的基本结构：

![](https://i-blog.csdnimg.cn/blog_migrate/43c0c87c2302f037342f0152d1d75c2f.png)

组成部分说明：
`Broker`：消息队列服务进程，此进程包括两个部分：`Exchange`和`Queue`
`Exchange`：消息队列交换机，按一定的规则将消息路由转发到某个队列，对消息进行过滤。

`Queue`：消息队列，存储消息的队列，消息到达队列并转发给指定的消费者
`Producer`：消息生产者，即生产方客户端，生产方客户端将消息发送
`Consumer`：消息消费者，即消费方客户端，接收`MQ`转发的消息。

***

生产者发送消息流程：
1、生产者和`Broker`建立`TCP`连接。
2、生产者和`Broker`建立通道。
3、生产者通过通道消息发送给`Broker`，由`Exchange`将消息进行转发。
4、`Exchange`将消息转发到指定的`Queue`（队列）



消费者接收消息流程：
1、消费者和`Broker`建立`TCP`连接
2、消费者和`Broker`建立通道
3、消费者监听指定的`Queue`（队列）

4、当有消息到达`Queue`时`Broker`默认将消息推送给消费者。
5、消费者接收到消息。
6、`ack`回复

***

## 4.1 RabbitMQ支持的消息模型

![image-20191126165434784](编程不良人/RibbitMQ 实战教程.assets/image-20191126165434784.png)

![image-20191126165459282](编程不良人/RibbitMQ 实战教程.assets/image-20191126165459282.png)

## 4.2 模型1(直连)

![image-20191126165840602](编程不良人/RibbitMQ 实战教程.assets/image-20191126165840602.png)

在上图的模型中，有以下概念：

- P：生产者，也就是要发送消息的程序
- C：消费者：消息的接受者，会一直等待消息到来。
- queue：消息队列，图中红色部分。类似一个邮箱，可以缓存消息；生产者向其中投递消息，消费者从其中取出消息。

## 4.3  模型2(work quene)

`Work queues`，也被称为（`Task queues`），任务模型。当消息处理比较耗时的时候，可能生产消息的速度会远远大于消息的消费速度。长此以往，消息就会堆积越来越多，无法及时处理。此时就可以使用work 模型：**让多个消费者绑定到一个队列，共同消费队列中的消息**。队列中的消息一旦消费，就会消失，因此任务是不会被重复执行的。

![image-20200314221002008](编程不良人/RibbitMQ 实战教程.assets/image-20200314221002008.png)

角色：

- P：生产者：任务的发布者
- C1：消费者-1，领取任务并且完成任务，假设完成速度较慢
- C2：消费者-2：领取任务并完成任务，假设完成速度快

## 4.4 模型3(fanout) 

`fanout 扇出 也称为广播`

![image-20191126213115873](编程不良人/RibbitMQ 实战教程.assets/image-20191126213115873.png)

在广播模式下，消息发送流程是这样的：

-  可以有多个消费者
-  每个**消费者有自己的queue**（队列）
-  每个**队列都要绑定到Exchange**（交换机）
-  **生产者发送的消息，只能发送到交换机**，交换机来决定要发给哪个队列，生产者无法决定。
-  交换机把消息发送给绑定过的所有队列
-  队列的消费者都能拿到消息。实现一条消息被多个消费者消费

## 4.5 模型4(Routing)

`在Fanout模式中，一条消息，会被所有订阅的队列都消费。但是，在某些场景下，我们希望不同的消息被不同的队列消费。这时就要用到Direct类型的Exchange。`

 在Direct模型下：

- 队列与交换机的绑定，不能是任意绑定了，而是要指定一个`RoutingKey`（路由key）
- 消息的发送方在 向 Exchange发送消息时，也必须指定消息的 `RoutingKey`。
- Exchange不再把消息交给每一个绑定的队列，而是根据消息的`Routing Key`进行判断，只有队列的`Routingkey`与消息的 `Routing key`完全一致，才会接收到消息

流程:

![image-20191126220145375](编程不良人/RibbitMQ 实战教程.assets/image-20191126220145375.png)

图解：

- P：生产者，向Exchange发送消息，发送消息时，会指定一个routing key。
- X：Exchange（交换机），接收生产者的消息，然后把消息递交给 与routing key完全匹配的队列
- C1：消费者，其所在队列指定了需要routing key 为 error 的消息
- C2：消费者，其所在队列指定了需要routing key 为 info、error、warning 的消息

## 4.6 模型5(Topic)

`Topic`类型的`Exchange`与`Direct`相比，都是可以根据`RoutingKey`把消息路由到不同的队列。只不过`Topic`类型`Exchange`可以让队列在绑定`Routing key` 的时候使用通配符！这种模型`Routingkey` 一般都是由一个或多个单词组成，多个单词之间以”.”分割，例如： `item.insert`

![image-20191127121900255](编程不良人/RibbitMQ 实战教程.assets/image-20191127121900255.png)

# 5消息确认机制

## 5.1 生产者消息确认(消息可靠性投递)

项目的`application.yml`文件上，加上消息确认的配置项后：

`ps`： 本篇文章使用`springboot`版本为`2.1.7.RELEASE`;
如果你们在配置确认回调，测试发现无法触发回调函数，那么存在原因也许是因为版本导致的配置项不起效，可以把`publisher-confirms: true `替换为 `publisher-confirm-type: correlated`

```yaml
server:
  port: 8021
spring:
  #给项目来个名字
  application:
    name: rabbitmq-provider
  #配置rabbitMq 服务器
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: root
    password: root
    #虚拟host 可以不设置,使用server默认host
    virtual-host: JCcccHost
    #消息确认配置项
 
    #确认消息已发送到交换机(Exchange)
    publisher-confirms: true
    #确认消息已发送到队列(Queue)
    publisher-returns: true
```

然后是配置相关的消息确认回调函数，`RabbitConfig.java`：

```java
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
 
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
 
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ack);
                System.out.println("ConfirmCallback:     "+"原因："+cause);
            }
        });
 
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("ReturnCallback:     "+"消息："+message);
                System.out.println("ReturnCallback:     "+"回应码："+replyCode);
                System.out.println("ReturnCallback:     "+"回应信息："+replyText);
                System.out.println("ReturnCallback:     "+"交换机："+exchange);
                System.out.println("ReturnCallback:     "+"路由键："+routingKey);
            }
        });
        return rabbitTemplate;
    }
}

```

到这里，生产者推送消息的消息确认调用回调函数已经完毕。
可以看到上面写了两个回调函数， `ConfirmCallback` 和`RetrunCallback`；
那么以上这两种回调函数都是在什么情况会触发呢？

先从总体的情况分析，推送消息存在四种情况：
1、消息推送到`server`，但是在`server`里找不到交换机
2、消息推送到`server`，找到交换机了，但是没找到队列
3、消息推送成功

那么我先写几个接口来分别测试和认证下以上`4`种情况，消息确认触发回调函数的情况：

***

### 5.1.1 **消息推送到`server`，但是在`server`里找不到交换机**

写个测试接口，把消息推送到名为`non-existent-exchange`的交换机上（这个交换机是没有创建没有配置的）：

调用接口，查看控制台输出情况（原因里面有说，没有找到交换机`non-existent-exchange`）：

```java
2019-09-04 09:37:45.197 ERROR 8172 --- [ 127.0.0.1:5672] o.s.a.r.c.CachingConnectionFactory       : Channel shutdown: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'non-existent-exchange' in vhost 'JCcccHost', class-id=60, method-id=40)
ConfirmCallback:     相关数据：null
ConfirmCallback:     确认情况：false
ConfirmCallback:     原因：channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'non-existent-exchange' in vhost 'JCcccHost', class-id=60, method-id=40)
```

> **结论： 这种情况触发的是 `ConfirmCallback` 回调函数。**

### 5.1.2 **消息推送到`server`，找到交换机了，但是没找到队列**

这种情况就是需要新增一个交换机，但是不给这个交换机绑定队列，我来简单地在`DirectRabitConfig`里面新增一个直连交换机，名叫`lonelyDirectExchange`，但没给它做任何绑定配置操作：

调用接口，查看控制台输出情况:

```java
ReturnCallback:     消息：(Body:'{createTime=2019-09-04 09:48:01, messageId=563077d9-0a77-4c27-8794-ecfb183eac80, messageData=message: lonelyDirectExchange test message }' MessageProperties [headers={}, contentType=application/x-java-serialized-object, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, deliveryTag=0])
ReturnCallback:     回应码：312
ReturnCallback:     回应信息：NO_ROUTE
ReturnCallback:     交换机：lonelyDirectExchange
ReturnCallback:     路由键：TestDirectRouting
ConfirmCallback:     相关数据：null
ConfirmCallback:     确认情况：true
ConfirmCallback:     原因：null
```

可以看到这种情况，两个函数都被调用了；
这种情况下，消息是推送成功到服务器了的，所以`ConfirmCallback`对消息确认情况是`true`；

而在`RetrunCallback`回调函数的打印参数里面可以看到，消息是推送到了交换机成功了，但是在路由分发给队列的时候，找不到队列，所以报了错误 `NO_ROUTE` 。

**结论：这种情况触发的是 `ConfirmCallback`和`RetrunCallback`两个回调函数**。

***

### 5.1.3 **消息推送成功**

调用接口，查看控制台输出情况:

```java
ConfirmCallback:     相关数据：null
ConfirmCallback:     确认情况：true
ConfirmCallback:     原因：null
```

**结论： 这种情况触发的是`ConfirmCallback`回调函数。**

以上是生产者推送消息的消息确认 回调函数的使用介绍（可以在回调函数根据需求做对应的扩展或者业务数据处理）。

***

## 5.2 消费者消息确认

### 5.2.1 介绍

消费者确认机制（Consumer Acknowledgement）是为了确认消费者是否成功处理消息。当消费者处理消息结束后，应该向[RabbitMQ](https://so.csdn.net/so/search?q=RabbitMQ&spm=1001.2101.3001.7020)发送一个回执，告知RabbitMQ自己消息处理状态：

![](https://i-blog.csdnimg.cn/direct/cbcfc62d9fc8480a92dd98f839ecc97c.png)

ack：成功处理消息，RabbitMQ从队列中删除该消息

nack：消息处理失败，RabbitMQ可根据参数设置是否重新投递

reject：拒绝消息处理，RabbitMQ可根据参数设置是否重新投递

> nack和reject的区别：
>
> 业务上：
>
> - nack：消费者拿到正确的消息，自己处理出错了（可以设置成需要重新投递）；
>
> - reject：消费者拿到不正确的消息，拒绝处理消息（可以设置成不需要重新投递，MQ可以直接丢弃这样的消息）；
>
> 处理上【看6消费者批量消费讲解】：
>
> - nack：可以批量处理消息；
>
> - reject：只能单个消息处理；

SpringAMQP已经实现了消息确认功能。并允许我们通过[配置文件](https://so.csdn.net/so/search?q=配置文件&spm=1001.2101.3001.7020)选择ACK处理方式，有三种方式：

> **none：**不处理 消息投递给消费者后立刻ack 消息立刻从MQ删除（非常不安全不建议使用）
>
> **manual【个人推荐使用】：**手动模式 即手动ack或reject，需要在业务代码结束后，调用api发送ack，但是这种有代码入侵
>
> **auto：**自动模式 SpringAMQP利用[AOP](https://so.csdn.net/so/search?q=AOP&spm=1001.2101.3001.7020)对我们的消息处理逻辑做了环绕增强，当业务正常执行时则自动返回ack。当业务出现[异常](https://marketing.csdn.net/p/3127db09a98e0723b83b2914d9256174?pId=2782&utm_source=glcblog&spm=1001.2101.3001.7020)时，根据异常判断返回不同结果：
>
> 1. 如果是业务异常，会自动返回nack
>
> 2. 如果是消息处理或校验异常，自动返回reject

### 5.2.2 演示三种ACK方式效果

#### 5.2.2.1  none: 不处理

消费者配置代码

```yaml
spring:
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: Wangzhexiao
    password: Wangzhexiao
    virtual-host: /hangzhou
    listener:
      simple:
        prefetch: 1
        acknowledge-mode: none # none，关闭ack；manual，手动ack；auto：自动ack
```

生产者主要代码

```java
@Slf4j
@SpringBootTest
class PublisherApplicationTests {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Test
    void test() {
        rabbitTemplate.convertAndSend("simple.queue", "只要学不死，就往死里学！");
    }
}
```

消费者主要代码

```java
@Slf4j
@Component
public class SimpleListener {
    @RabbitListener(queues = "simple.queue")
    public void listener1(String msg) throws Exception {
//        System.out.println("消费者1：人生是个不断攀登的过程【" + msg + "】");
        throw new Exception();
    }
}
```

运行效果

![](https://i-blog.csdnimg.cn/direct/f5c6a91b3f0a4c42b66a62fdb09bd82a.png)

![](https://i-blog.csdnimg.cn/direct/4afa2c51686642629fe850b73b3c9f71.png)

![](https://i-blog.csdnimg.cn/direct/5f5ea4b200ac42ad88e6889594c88c72.png)

> 我们可以看到，当生产者投递到MQ的那一刻，会立刻返回ACK，此刻消费者的业务逻辑未执行完。

***

#### 5.2.2.2 **manual：**手动模式

> acknowledge-mode: manual

我们定义了一个SimpleMessageListenerContainer，并为它设置了一个ChannelAwareMessageListener。在监听器内部，我们实现了消息的接收和处理，并在处理完成后使用channel.basicAck方法手动发送一个确认消息给RabbitMQ，表明消息已被消费。如果在处理消息时发生异常，我们可以使用channel.basicReject方法拒绝该消息，以便RabbitMQ可以将其重新排队或者进行其他配置的处理。 

```java
@Configuration
public class RabbitMQConfig {
 
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("yourQueueName"); // 设置监听的队列名称
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                try {
                    // 消息处理逻辑
                    System.out.println("Received message: " + new String(message.getBody()));
 
                    // 确认消息已被成功处理
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } catch (Exception e) {
                    // 出现异常，拒绝该消息
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                }
            }
        });
        return container;
    }
}
```

#### 5.2.2.3 **auto：**自动模式

> acknowledge-mode: auto

当生产者投递到MQ后消费者在消费过程中发生业务异常，MQ会将它标记为Unacked，后续会一直投递该消息，直到消费成功为止。

![](https://i-blog.csdnimg.cn/direct/df8120df44b545ceba87b49f8c8860e8.png)



![](https://i-blog.csdnimg.cn/direct/584ca46cf89143088bdca6b066d669a4.png)

下图看到有两条消息，其中一条是第一次投递失败重新投递的消息： 

![](https://i-blog.csdnimg.cn/direct/77d3d325a5504645ba1d1bf99b4a6052.png)



> 至此我们思考一下，实际项目中如果采用Spring AMQP为我们实现的auto 自动模式确认机制，虽然看上去我们的系统设计简单了，但是对于如果我们业务代码出现异常，消息在消费过程中执行一直失败，那么RabbitMQ后续会一直投递该消息，这期间异常消息如果一直消费不了，循环投递就会给我们系统造成极大的压力负担，这该怎么解决？
>

***

### 5.2.3 消费者消息确认机制详细讲解

**1、自动确认， 这也是默认的消息确认情况。 `AcknowledgeMode.NONE`**
`RabbitMQ`成功将消息发出（即将消息成功写入`TCP Socket`）中**立即认为本次投递已经被正确处理，不管消费者端是否成功处理本次投递**。

所以这种情况如果消费端消费逻辑抛出异常，也就是消费端没有处理成功这条消息，那么就相当于**丢失了消息**。
一般这种情况我们都是使用`try catch`捕捉异常后，打印日志用于追踪数据，这样找出对应数据再做后续处理。

**2、手动确认 ， 这个比较关键，也是我们配置接收消息确认机制时，多数选择的模式。**

消费者收到消息后，手动调用`basic.ack/basic.nack/basic.reject`后，`RabbitMQ`收到这些消息后，才认为本次投递成功。

`basic.ack`用于肯定确认
`basic.nack`用于否定确认（注意：这是AMQP 0-9-1的RabbitMQ扩展）
`basic.reject`用于否定确认，但与`basic.nack`相比有一个限制：一次只能拒绝单条消息

消费者端以上的3个方法都表示消息已经被正确投递，但是`basic.ack`表示消息已经被正确处理。
而`basic.nack,basic.reject`表示没有被正确处理：

着重讲下`reject`，因为有时候一些场景是需要重新入列的。

`channel.basicReject(deliveryTag, true)`; 拒绝消费当前消息，如果第二参数传入`true`，就是将数据重新丢回队列里，那么下次还会消费这消息。设置`false`，就是告诉服务器，我已经知道这条消息数据了，因为一些原因拒绝它，而且服务器把这个消息丢掉就行，下次不想再消费这条消息了。

使用拒绝后重新入列这个确认模式要谨慎，因为一般都是出现异常的时候，`catch`异常再拒绝入列，选择是否重入列。

但是如果使用不当会导致一些每次都被你重入列的消息一直消费-入列-消费-入列这样循环，会导致消息积压。

顺便也简单讲讲 `nack`，这个也是相当于设置不消费某条消息。

> channel.basicNack(deliveryTag, false, true); 

第一个参数依然是当前消息到的数据的唯一`id`;
**第二个参数是指是否针对多条消息；如果是true，也就是说一次性针对当前通道的消息的`tagID`小于当前这条消息的，都拒绝确认【这就是 nack与reject的区别，nack可以批量处理多条数据】**。
第三个参数是指是否重新入列，也就是指不确认的消息是否重新丢回到队列里面去。

同样使用不确认后重新入列这个确认模式要谨慎，因为这里也可能因为考虑不周出现消息一直被重新丢回去的情况，导致积压。

**看了上面这么多介绍，接下来我们一起配置下，看看一般的消息接收手动确认是怎么样的。**

在消费者项目里，新建`MessageListenerConfig.java`上添加代码相关的配置代码：

```java
import com.elegant.rabbitmqconsumer.receiver.MyAckReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageListenerConfig {
 
    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private MyAckReceiver myAckReceiver;//消息接收处理类
 
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        // RabbitMQ默认是自动确认，这里改为手动确认消息
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); 
        //设置一个队列
        container.setQueueNames("TestDirectQueue");
        //如果同时设置多个如下： 前提是队列都是必须已经创建存在的
        //  container.setQueueNames("TestDirectQueue","TestDirectQueue2","TestDirectQueue3");
 
        //另一种设置队列的方法,如果使用这种情况,那么要设置多个,就使用addQueues
        //container.setQueues(new Queue("TestDirectQueue",true));
        //container.addQueues(new Queue("TestDirectQueue2",true));
        //container.addQueues(new Queue("TestDirectQueue3",true));
        container.setMessageListener(myAckReceiver);
 
        return container;
    }
}

```

对应的手动确认消息监听类，MyAckReceiver.java（手动确认模式需要实现 ChannelAwareMessageListener）：
//之前的相关监听器可以先注释掉，以免造成多个同类型监听器都监听同一个队列。
//这里的获取消息转换，只作参考，如果报数组越界可以自己根据格式去调整。

```java
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
 
@Component
 
public class MyAckReceiver implements ChannelAwareMessageListener {
 
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            String msg = message.toString();
            String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim(),3);
            String messageId=msgMap.get("messageId");
            String messageData=msgMap.get("messageData");
            String createTime=msgMap.get("createTime");
            System.out.println("  MyAckReceiver  messageId:"+messageId+"  messageData:"+messageData+"  createTime:"+createTime);
            System.out.println("消费的主题消息来自："+message.getMessageProperties().getConsumerQueue());
            channel.basicAck(deliveryTag, true); //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
//			channel.basicReject(deliveryTag, true);//第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }
 
     //{key=value,key=value,key=value} 格式转换成map
    private Map<String, String> mapStringToMap(String str,int entryNum ) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",",entryNum);
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}
```

# 6消费者批量消费

**为什么要用消费端批量消费？**

在一些[业务场景](https://edu.csdn.net/cloud/pm_summit?utm_source=blogglc&spm=1001.2101.3001.7020)下，我们希望使用 Consumer 批量消费消息，提高消费速度。可以通过对 SimpleRabbitListenerContainerFactory 进行配置实现批量消费能力

```java
==========================>配置类
@Configuration
public class ConsumerConfiguration {
    @Resource
    ConnectionFactory connectionFactory;
    @Resource
    SimpleRabbitListenerContainerFactoryConfigurer configurer;

    /**
     * 配置一个批量消费的 SimpleRabbitListenerContainerFactory
     */
    @Bean(name = "consumer10BatchContainerFactory")
    public SimpleRabbitListenerContainerFactory consumer10BatchContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        // 这里是重点 配置消费者的监听器是批量消费消息的类型
        factory.setBatchListener(true);

        // 一批十个
        factory.setBatchSize(10);
        // 等待时间 毫秒 , 这里其实是单个消息的等待时间 指的是单个消息的等待时间
        // 也就是说极端情况下，你会等待 BatchSize * ReceiveTimeout 的时间才会收到消息
        factory.setReceiveTimeout(10 * 1000L);
        factory.setConsumerBatchEnabled(true);

        return factory;
    }
}
====================》生产者
@Component
public class Producer10 {

    @Resource
    RabbitTemplate rabbitTemplate;

    public void sendSingle(String id, String routingKey) {
        Message10 message = new Message10();
        message.setId(id);
        rabbitTemplate.convertAndSend(Message10.EXCHANGE, routingKey, message);
    }
}
================================》消费者
@RabbitListener(queues = Message10.QUEUE, containerFactory = "consumer10BatchContainerFactory")
@Component
@Slf4j
public class Consumer10 {
    /**
     * 批量消费
     *
     * @param message 一批消息
     */
    @RabbitHandler
    public void onMessage(List<Message10> message) {
        log.info("[{}][Consumer10 批量][线程编号:{}][消息个数：{}][消息内容：{}]"
                , LocalDateTime.now()
                , Thread.currentThread().getId()
                , message.size()
                , message);
    }

    /**
     * 单个消费
     *
     * @param message 一个消息
     */
    @RabbitHandler
    public void onMessage(Message10 message) {
        log.info("[{}][Consumer10 单个][线程编号:{}][消息内容：{}]"
                , LocalDateTime.now()
                , Thread.currentThread().getId()
                , message);
    }
}
==================================》测试类
@Test
    void sendSingle() throws InterruptedException {
        // 假设 一秒一个，发送 1000 个，观察消费者的情况
        for (int i = 0; i < 15; i++) {
            TimeUnit.SECONDS.sleep(1);
            String id = UUID.randomUUID().toString();
            producer10.sendSingle(id, Message10.ROUTING_KEY);
            if (i == 9) {
                log.info("[{}][test producer10 sendSingle] 发送成功10个", LocalDateTime.now());
            }
        }
        log.info("[{}][test producer10 sendSingle] 发送成功", LocalDateTime.now());

        TimeUnit.SECONDS.sleep(20);
    }
}
```

# 7 失败消息处理策略

> 上面说到如果消息拒绝重回队列，又回重新投递，消费者重新消费，循环投递就会给我们系统造成极大的压力负担

## 7.1 引言

Spring [AMQP](https://so.csdn.net/so/search?q=AMQP&spm=1001.2101.3001.7020)提供了消费者失败重试机制，在消费者出现[异常](https://marketing.csdn.net/p/3127db09a98e0723b83b2914d9256174?pId=2782&utm_source=glcblog&spm=1001.2101.3001.7020)时利用本地重试，而不是无限地requeue到mq。我们可以通过在application.yaml文件中添加配置来开启重试机制：

```yaml
spring:
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: Wangzhexiao
    password: Wangzhexiao
    virtual-host: /hangzhou
    listener:
      simple:
        prefetch: 1
        acknowledge-mode: manual # none，关闭ack；manual，手动ack；auto：自动ack
        # 消费者重试机制配置
        retry:
          enabled: true # 开启消费者失败重试
          initial-interval: 1000ms # 初始的失败等待时长为1秒
          multiplier: 1 # 下次失败的等待时长倍数，下次等待时长 = multiplier * last-interval
          max-attempts: 3 # 最大重试次数
          stateless: true # true无状态；false有状态。如果业务中包含事务，这里改为false
```

在开启重试模式后，重试次数耗尽，如果消息依然失败，则需要有MessageRecoverer接口来处理，它包含三种[不同的](https://so.csdn.net/so/search?q=不同的&spm=1001.2101.3001.7020)实现：

> **RejectAndDontRequeueRecoverer：**重试耗尽后，直接reject，丢弃消息（默认方式）
>
> **ImmediateRequeueMessageRecoverer：**重试耗尽后，返回nack，消息重新入队
>
> **RepublishMessageRecoverer：**重试耗尽后，将失败消息投递到指定的[交换机](https://so.csdn.net/so/search?q=交换机&spm=1001.2101.3001.7020)（推荐）

## 7.2 **RepublishMessageRecoverer 实现**

在实际项目的生产环境中，通过 **RepublishMessageRecoverer 方式**我们可以定义一个异常[队列](https://edu.csdn.net/course/detail/40020?utm_source=glcblog&spm=1001.2101.3001.7020)和交换机，来接收其他交换机队列转发的无法处理的异常消息。然后我们可以查看其中的异常消息并进行人工处理

![](https://i-blog.csdnimg.cn/direct/55df0cd63c324104bd2de36d2be997d3.png)

### 7.2.1 实现步骤

1. 将失败处理策略改为RepublishMessageRecoverer

2. 定义接收失败消息的交换机、队列及其绑定关系

3. 定义RepublishMessageRecoverer

### 7.2.2 实现代码

#### 7.2.2.1 异常交换机队列回收期配置类

```java
package com.example.consumer;
 
import jakarta.annotation.Resource;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
/**
 * 异常交换机/队列/消息回收器配置类
 * ConditionalOnProperty 通过yml中的重试配置来选择该配置类是否启用
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.rabbitmq.listener.simple.retry", name = "enabled", havingValue = "true")
public class ErrorConfig {
 
    @Resource
    private RabbitTemplate rabbitTemplate;
 
    @Bean
    Queue errorQueue() {
        return new Queue("error.queue");
    }
 
    @Bean
    DirectExchange errorExchange() {
        return new DirectExchange("error.direct");
    }
 
    @Bean
    Binding errorBind(Queue errorQueue, DirectExchange errorExchange) {
        return BindingBuilder.bind(errorQueue).to(errorExchange).with("error");
    }
 
    @Bean
    public MessageRecoverer messageRecoverer() {
        return new RepublishMessageRecoverer(rabbitTemplate, "error.direct", "error");
    }
}
```

#### 7.2.2.2

常规交换机队列配置类

```java
package com.example.consumer;
 
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
/**
 * 常规的RabbitMQ 交换机/队列绑定配置类
 */
@Configuration
public class RabbitMQConfig {
 
    @Bean
    Queue simpleQueue() {
        // 使用 QueueBuilder 创建一个持久化队列
        return QueueBuilder.durable("simple.queue").build();
    }
}
```

#### 7.2.2.3 消费者代码

```java
package com.example.consumer;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
/**
 * 消费者
 */
@Slf4j
@Component
public class SimpleListener {
 
    @RabbitListener(queues = "simple.queue")
    public void listener1(String msg) throws Exception {
//        System.out.println("消费者1：人生是个不断攀登的过程【" + msg + "】");
        throw new Exception();
    }
}
```

#### 7.2.2.4 消费者yml配置

```yaml
# 消费者application.yml配置
spring:
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: Wangzhexiao
    password: Wangzhexiao
    virtual-host: /hangzhou
    listener:
      simple:
        prefetch: 1
        acknowledge-mode: auto # none，关闭ack；manual，手动ack；auto：自动ack
        # 消费者重试机制配置
        retry:
          enabled: true # 开启消费者失败重试
          initial-interval: 1000ms # 初始的失败等待时长为1秒
          multiplier: 1 # 下次失败的等待时长倍数，下次等待时长 = multiplier * last-interval
          max-attempts: 3 # 最大重试次数
          stateless: true # true无状态；false有状态。如果业务中包含事务，这里改为false
```

#### 7.2.2.5 生产者代码 

```java
package com.example.publisher;
 
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
 
/**
 * 生产者
 */
@Slf4j
@SpringBootTest
class PublisherApplicationTests {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Test
    void test() {
        rabbitTemplate.convertAndSend("simple.queue", "只要学不死，就往死里学！");
    }
}
```

#### 7.2.2.6 生产者yml配置

```yaml
# 生产者application.yml配置
spring:
  rabbitmq:
    # MQ连接配置
    host: 127.0.0.1
    port: 5672
    username: Wangzhexiao
    password: Wangzhexiao
    virtual-host: /hangzhou
```

### 7.2.3 运行效果

最终效果是，我们在消费者的代码逻辑中会抛出异常，消息在反复投递消费失败后被重新入列到我们定义的异常交换机队列中：

![](https://i-blog.csdnimg.cn/direct/ffc275a15e15460aad0008ecb4a52152.png)

![](https://i-blog.csdnimg.cn/direct/06b658f78a5b4762b2c7ffcff33f70aa.png)

***

# 8 Rabbitmq 消息分发

## 8.1 消息分发

> **RabbitMQ队列拥有多个消费者时,队列会把收到的消息分派给不同的消费者.每条消息只会发送给订阅列表里的⼀个消费者.这种方式⾮常适合扩展,如果现在负载加重,那么只需要创建更多的消费者来消费处理消息即可.**
>
> **默认情况下,RabbitMQ是以\**轮询的方法\**进行分发的,而不管消费者是否已经消费并已经确认了消息.这种方式是不太合理的,试想⼀下,如果某些消费者消费速度慢,而某些消费者消费速度快,就可能会导致某些消费者消息积压,某些消费者空闲,进而应用整体的吞吐量下降**
>
> **这样A都做完了10个任务，B还在写第一个任务，这样将会大大影响效率，从而导致整个的效率下降**
>
> **如何处理呢我们可以使用前面章节讲到的\**channel.basicQos(intprefetchCount)\**方法,来限制当前信道上的消费者所能保持的最大未确认消息的数量**
>
> **比如:消费端调用了\**channelbasicQos(1),\****
>
> **此时A接收1条信息，并且消费1条 B同时也接收1条信息 但是它效率比较慢 所有它还在消费 而A处理完1条消息又接着处理第二条消息，属于多劳多得，并不会因为B影响整体的效率**

### 8.1.1 限流

**如下使用场景:
订单系统每秒最多处理5000请求,正常情况下,订单系统可以正常满足需求
但是在秒杀时间点,请求瞬间增多,每秒1万个请求,如果这些请求全部通过MQ发送到订单系统,无疑会把订单系统压垮.**

![](https://i-blog.csdnimg.cn/direct/5e90f77b687340cdb94226622bb7b3ea.png)

**RabbitMQ提供了限流机制,可以控制消费端⼀次只拉取N个请求
通过设置\**prefetchCount\**参数,同时也必须要设置消息应答方式为手动应答
\**prefetchCount:控制消费者从队列中预取(prefetch)消息的数量,以此来实现流控制和负载均衡.\****

**1） 配置prefetch参数,设置应答方式为手动应答** 

![](https://i-blog.csdnimg.cn/direct/c343a4792a474ed596895c66d1022847.png)

 **2） 配置交换机,队列**

```java
package com.bite.extensions.config;
 
import com.bite.extensions.constant.Constants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration
public class QosConfig {
 
    @Bean("qosQueue")
    public Queue qosQueue() {
        return QueueBuilder.durable(Constants.QOS_QUEUE).build();
    }
    @Bean("qosExchange")
    public DirectExchange qosExchange() {
        return  ExchangeBuilder.directExchange(Constants.QOS_EXCHANGE).build();
    }
    @Bean("qosBinding")
    public Binding qosBinding(@Qualifier("qosQueue") Queue queue, @Qualifier("qosExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("qos");
    }
}
```

3) 生产者

```java
@RequestMapping("/qos")
public String qos() {
    System.out.println("qos test...");
    for (int i = 0; i < 15; i++) {
        rabbitTemplate.convertAndSend(Constants.QOS_EXCHANGE, "qos", "qos test i..."+i);
    }
    return "消息发送成功";
}
```

4）消费者

```java
package com.bite.extensions.listener;
 
import com.bite.extensions.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
@Component
public class QosListener {
    @RabbitListener(queues = Constants.QOS_QUEUE)
    public void handleMessage(Message message, Channel channel) throws Exception {
        //消费者逻辑
        long deliverTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.printf("[qos.queue]接收到信息: %s, deliveryTag: %d\n",new String(message.getBody(),"UTF-8"),deliverTag);
/*            //业务逻辑处理
            System.out.println("业务逻辑处理！");
            //肯定确认
            channel.basicAck(deliverTag,false);*/
        } catch (Exception e) {
            //否定确认
            channel.basicNack(deliverTag,false,true);//requeue为false，则变成死信队列
        }
    }
}
```

 **5）测试1 未设置肯定确认情况**

![](https://i-blog.csdnimg.cn/direct/e88887e4f2e54e948de2a92bd76d4203.png)

![](https://i-blog.csdnimg.cn/direct/6a1d3d3f5c3a4da6b66709738deb97e4.png)

![](https://i-blog.csdnimg.cn/direct/255520e626b842249cee298ab0a37015.png)

![](https://i-blog.csdnimg.cn/direct/e95a0179b2af44798246050dcd7a961f.png)

![](https://i-blog.csdnimg.cn/direct/4ed01553bdc6429e93a7e17508a24ff8.png)

> 此时将会只接收到5条，并且会阻塞住，达到一个限流的状态

**测试2**

**把 prefetch: 5 注掉 再观看结果**

![](https://i-blog.csdnimg.cn/direct/03d062648e074ecb9a8ada1cd4044181.png)

> **此时将会一次性把队列的消息全部发送，并且全部消费**



### 8.1.2 负载均衡

**在有两个消费者的情况下，⼀个消费者处理任务非常快,另⼀个非常慢,就会造成⼀个消费者会⼀直很忙,而另⼀个消费者很闲.这是因为RabbitMQ只是在消息进入队列时分派消息.它不考虑消费者未确认消息的数量.**

**我们可以使用设置\**prefetch=1\**的⽅式,告诉RabbitMQ⼀次只给⼀个消费者⼀条消息,也就是说,在处理并确认前⼀条消息之前,不要向该消费者发送新消息.相反,它会将它分派给下⼀个不忙的消费者.**

![](https://i-blog.csdnimg.cn/direct/65889132153343dfbefda3fbbd1bfe6a.png)

**消费者：** 

```java
package com.bite.extensions.listener;
 
import com.bite.extensions.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
@Component
public class QosListener {
    @RabbitListener(queues = Constants.QOS_QUEUE)
    public void handleMessage(Message message, Channel channel) throws Exception {
        //消费者逻辑
        long deliverTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.printf("第一个消费者 接收到信息: %s, deliveryTag: %d\n",new String(message.getBody(),"UTF-8"),deliverTag);
            Thread.sleep(3000);
            channel.basicAck(deliverTag,false);
        } catch (Exception e) {
            //否定确认
            channel.basicNack(deliverTag,false,true);//requeue为false，则变成死信队列
        }
    }
    @RabbitListener(queues = Constants.QOS_QUEUE)
    public void handleMessage2(Message message, Channel channel) throws Exception {
        //消费者逻辑
        long deliverTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.printf("第二个消费者 接收到信息: %s, deliveryTag: %d\n",new String(message.getBody(),"UTF-8"),deliverTag);
            Thread.sleep(1000);
            channel.basicAck(deliverTag,false);
        } catch (Exception e) {
            //否定确认
            channel.basicNack(deliverTag,false,true);//requeue为false，则变成死信队列
        }
    }
}
```

![](https://i-blog.csdnimg.cn/direct/dac9a94bcb6842f9b3eaab9fb6db2c64.png)

> **这里可以看出每个消费者以不同的速度完成某项任务 以防止一个消费者未完成等很久的情况**

