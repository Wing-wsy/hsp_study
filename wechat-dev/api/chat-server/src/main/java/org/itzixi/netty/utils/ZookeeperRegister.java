package org.itzixi.netty.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.itzixi.base.BaseInfoProperties;
import org.itzixi.constant.basic.Strings;
import org.itzixi.pojo.netty.NettyServerNode;
import org.itzixi.utils.JsonUtils;
import org.itzixi.utils.LocalDateUtils;

import java.net.InetAddress;
import java.util.List;

/**
 * @Auther
 */
public class ZookeeperRegister extends BaseInfoProperties {

    public static void registerNettyServer(String nodeName,
                                           String ip,
                                           Integer port) throws Exception {
        CuratorFramework zkClient = CuratorConfig.getClient();
        String path = "/" + nodeName;
        Stat stat = zkClient.checkExists().forPath(path);
        // 节点不存在则创建
        if (stat == null) {
            zkClient.create()
                    .creatingParentsIfNeeded() // 支持递归创建
                    .withMode(CreateMode.PERSISTENT) // 节点类型：永久
                    .forPath(path);
        } else {
            System.out.println(stat.toString());
        }

        // 创建对应的临时节点，值可以放在线人数，默认为初始化的0
        NettyServerNode serverNode = new NettyServerNode();
        serverNode.setIp(ip);
        serverNode.setPort(port);
        String nodeJson = JsonUtils.objectToJson(serverNode);

        zkClient.create()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)// 节点类型：临时
                .forPath(path + "/im-", nodeJson.getBytes());
    }

    // 上生产后，这个获取IP可以写死 dev
//    public static String getLocalIp() throws Exception {
//        InetAddress addr = InetAddress.getLocalHost();
//        String ip=addr.getHostAddress();
//        System.out.println("本机IP地址：" + ip);
//        // ip = xx.xx.xx.xx; 写死
//        return ip;
//    }
    // 上生产后，这个获取IP可以写死 prod
    public static String getLocalIp() throws Exception {
        // TODO 注意生产多个不同服务器部署集群，这里不能写死，应该动态获取当前服务器的IP返回
        return "8.217.253.19";
    }

    /**
     * 增加在线人数
     * @param serverNode
     */
    public static void incrementOnlineCounts(NettyServerNode serverNode) throws Exception {
        dealOnlineCounts(serverNode, 1);
    }

    /**
     * 减少在线人数
     * @param serverNode
     */
    public static void decrementOnlineCounts(NettyServerNode serverNode) throws Exception {
        dealOnlineCounts(serverNode, -1);
    }

    /**
     * 处理在线人数的增减
     * @param serverNode
     * @param counts
     */
    public static void dealOnlineCounts(NettyServerNode serverNode,
                                        Integer counts) throws Exception {
        CuratorFramework zkClient = CuratorConfig.getClient();
        // 锁对象
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(zkClient,
                                                                        "/rw-locks");
        // 获取锁
        readWriteLock.writeLock().acquire();
        try {
            String path = Strings.SLASH + SERVER_LIST;
            List<String> list = zkClient.getChildren().forPath(path);
            for (String node:list) {
                String pendingNodePath = path + "/" + node;
                String nodeValue = new String(zkClient.getData().forPath(pendingNodePath));
                NettyServerNode pendingNode = JsonUtils.jsonToPojo(nodeValue,
                                                                   NettyServerNode.class);

                // 如果ip和端口匹配，则当前路径的节点则需要累加或者累减
                if (pendingNode.getIp().equals(serverNode.getIp()) &&
                        (pendingNode.getPort().intValue() == serverNode.getPort().intValue())) {
                    pendingNode.setOnlineCounts(pendingNode.getOnlineCounts() + counts);
                    String nodeJson = JsonUtils.objectToJson(pendingNode);
                    zkClient.setData().forPath(pendingNodePath, nodeJson.getBytes());
                }
            }
        } finally {
            // 释放锁
            readWriteLock.writeLock().release();
        }
    }

}

