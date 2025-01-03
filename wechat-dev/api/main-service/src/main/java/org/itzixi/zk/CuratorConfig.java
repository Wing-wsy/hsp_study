package org.itzixi.zk;

import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.constant.basic.Strings;
import org.itzixi.pojo.netty.NettyServerNode;
import org.itzixi.utils.JsonUtils;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@ConfigurationProperties(prefix = "zookeeper.curator")
@Data
public class CuratorConfig extends BaseInfoProperties {

    private String host;                    // 单机/集群的ip:port地址
    private Integer connectionTimeoutMs;    // 连接超时时间
    private Integer sessionTimeoutMs;         // 会话超时时间
    private Integer sleepMsBetweenRetry;    // 每次重试的间隔时间
    private Integer maxRetries;             // 最大重试次数
    private String namespace;               // 命名空间（root根节点名称）

    // 监听的路径
    public static final String path = Strings.SLASH + SERVER_LIST;

    @Bean("curatorClient")
    public CuratorFramework curatorClient() {
        // 三秒后重连一次，只连一次
        //RetryPolicy retryOneTime = new RetryOneTime(3000);
        // 每3秒重连一次，重连3次
        //RetryPolicy retryNTimes = new RetryNTimes(3, 3000);
        // 每3秒重连一次，总等待时间超过10秒则停止重连
        //RetryPolicy retryPolicy = new RetryUntilElapsed(10 * 1000, 3000);
        // 随着重试次数的增加，重试的间隔时间也会增加（推荐）
        RetryPolicy backoffRetry = new ExponentialBackoffRetry(sleepMsBetweenRetry, maxRetries);

        // 声明初始化客户端
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(host)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .retryPolicy(backoffRetry)
                .namespace(namespace)
                .build();
        client.start();     // 启动curator客户端

        // 注册事件【处理netty集群残留端口】
        add(path, client);

        return client;
    }

    @Resource
    private RabbitAdmin rabbitAdmin;

    /**
     * 注册节点的事件监听
     * @param path
     * @param client
     */
    public void add(String path, CuratorFramework client) {

        CuratorCache curatorCache = CuratorCache.build(client, path);
        curatorCache.listenable().addListener((type, oldData, data) -> {
            // type: 当前监听到的事件类型
            // oldData: 节点更新前的数据、状态
            // data: 节点更新后的数据、状态

//            if (oldData != null) {
//                log.info("oldData path:{},oldData value:{}", oldData.getPath(), oldData.getData());
//            }
//            if (data != null) {
//                log.info("new path:{},new value:{}", data.getPath(), data.getData());
//            }

            switch (type.name()) {
                case "NODE_CREATED":
//                    log.info("(子)节点创建");
                    break;
                case "NODE_CHANGED":
//                    log.info("(子)节点数据变更");
                    break;
                case "NODE_DELETED":
                    log.info("(子)节点删除");

                    NettyServerNode oldNode = JsonUtils.jsonToPojo(new String(oldData.getData()),
                                                                   NettyServerNode.class);

                    // 移除残留端口
                    String oldPort = oldNode.getPort() + "";
                    String portKey = "netty_port";
                    log.info("移除残留端口:{}",oldPort);
                    redis.hdel(portKey, oldPort);

                    // 移除残留端口
                    String onlineKey = "onlineCounts";
                    redis.hdel(onlineKey, oldPort);

                    // 移除残留队列
                    String queueName = NETTY_QUEUE_ + oldPort;
                    log.info("移除残留队列:{}",queueName);
                    rabbitAdmin.deleteQueue(queueName);

                    break;
                default:
                    log.info("default...{}",type.name());
                    break;
            }

        });

        // 开启监听
        curatorCache.start();
    }

}
