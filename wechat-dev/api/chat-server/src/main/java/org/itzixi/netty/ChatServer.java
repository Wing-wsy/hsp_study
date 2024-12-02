package org.itzixi.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.netty.mq.RabbitMQConnectUtils;
import org.itzixi.netty.utils.JedisPoolUtils;
import org.itzixi.netty.utils.ZookeeperRegister;
import org.itzixi.netty.websocket.ChatHandler;
import org.itzixi.netty.websocket.WSServerInitializer;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * ChatServer: Netty 服务的启动类(服务器)
 * @Auther
 */
public class ChatServer extends BaseInfoProperties {



    public static final String onlineKey = "onlineCounts";
    public static final Integer nettyDefaultPort = 875;
    public static Integer currentPort ;

    // 刷新在线人数
    public static void refreshOnlineCounts(){
        updateOnlineCounts();
    }

    // 更新在线人数到 redis
    public static void updateOnlineCounts () {
        Jedis jedis = JedisPoolUtils.getJedis();
        Map<String, String> onlineMap = jedis.hgetAll(onlineKey);
        for (String key : onlineMap.keySet()) {
            if (key.equals(String.valueOf(currentPort))) {
                jedis.hset(onlineKey, key, ChatHandler.clients.size()+"");
            }
        }
    }

    /*
     * 【该方法目前实现效果是，不同服务器部署集群，每个节点也不能使用相同的端口号比如:服务器1使用了 875端口，服务器2不能使用 875端口（实际不同的服务器可以使用相同的端口号）
     *  正因为每个节点也不能使用相同的端口号，所以 redis 可以使用 onlineCounts 来保存在线人数，否则的话会存在一些问题】
     * FIXME: 优化方案，改成zookeeper方案，
     *        如此可以不需要在中断连接后，监听并且清理在线人数和端口，
     *        因为netty与zk建立的临时节点，中断连接后，会自动删除该临时节点
     *
     * TODO表示还没做的事，就是还没写的代码。
     * FIXME表示可能有些功能已经实现了但不是最好的，或者存在异常隐患，需要后面更改的
     */
    public static Integer selectPort(Integer port) {
        String portKey = "netty_port";

        Jedis jedis = JedisPoolUtils.getJedis();

        Map<String, String> portMap = jedis.hgetAll(portKey);
//        System.out.println(portMap);
        // 由于map中的key都应该是整数类型的port，所以先转换成整数后，再比对，否则string类型的比对会有问题
        List<Integer> portList = portMap.entrySet().stream()
                .map(entry -> Integer.valueOf(entry.getKey()))
                .collect(Collectors.toList());
        // step1: 编码到此处先运行测试看一下结果
//        System.out.println(portList);

        Integer nettyPort = null;
        if (portList == null || portList.isEmpty()) {
            // step2: 编码到此处先运行测试看一下结果
            jedis.hset(portKey, port+"", "0");
            jedis.hset(onlineKey, port+"", "0");
            nettyPort = port;
            currentPort = port;
        } else {
            // 循环portList，获得最大值，并且累加10
            Optional<Integer> maxInteger = portList.stream().max(Integer::compareTo);
            Integer maxPort = maxInteger.get().intValue();
            Integer currentPortTemp = maxPort + 10;
            jedis.hset(portKey, currentPortTemp+"", "0");
            jedis.hset(onlineKey, currentPortTemp+"", "0");
            nettyPort = currentPortTemp;
            currentPort = currentPortTemp;
        }
        // step3: 编码到此处先运行测试看一下最终结果
        return nettyPort;

    }


    public static void main(String[] args) throws Exception {
        Integer port = nettyDefaultPort;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
            System.out.println("+++++++++++++++++++++++");
            System.out.println("port=" + port);
            System.out.println("+++++++++++++++++++++++");
        }

        /*
           定义主从线程组
           三种线程模型分别是：
           1单线程（一个线程处理全部的channel，即接收连接也处理消息）
           2多线程（一个线程只负责接收连接后，交给后面的线程组进行消息处理）
           3主从线程（多个线程只负责接收连接后，交给后面的线程组进行消息处理)
        */
        // 定义主线程池，用于接受客户端的连接，但是不做任何处理，比如老板会谈业务，拉到业务就会交给下面的员工去做了
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 定义从线程池，处理主线程池交过来的任务，公司业务员开展业务，完成老板交代的任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // Netty服务启动的时候，从redis中查找有没有端口，如果没有则用875，如果有则把端口累加1(或10)再启动
//        Integer nettyPort = selectPort(nettyDefaultPort);
        Integer nettyPort = selectPort(port);

        // 注册当前netty服务到zookeeper中
        ZookeeperRegister.registerNettyServer(SERVER_LIST,
                ZookeeperRegister.getLocalIp(),
                nettyPort);

        // 启动消费者进行监听，队列可以根据动态生成的端口号进行拼接
        String queueName = NETTY_QUEUE_ + nettyPort;
        RabbitMQConnectUtils mqConnectUtils = new RabbitMQConnectUtils();
        mqConnectUtils.listen("fanout_exchange", queueName);

        try {
            // 构建Netty服务器
            ServerBootstrap server = new ServerBootstrap();     // 服务的启动类
            server.group(bossGroup, workerGroup)                // 把主从线程池组放入到启动类中
                    .channel(NioServerSocketChannel.class)      // 设置Nio的双向通道
//                    .childHandler(new HttpServerInitializer());   // 设置处理器，用于处理workerGroup
                    .childHandler(new WSServerInitializer());   // 设置处理器，用于处理workerGroup

            // 启动server，并且绑定端口号875，同时启动方式为"同步"
            ChannelFuture channelFuture = server.bind(nettyPort).sync();
            // 请求：http://127.0.0.1:875

            // 监听关闭的channel
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅的关闭线程池组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
