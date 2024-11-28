package org.itzixi.netty.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther
 * Jedis 连接池工具类
 */
public class JedisPoolUtils {

    private static final JedisPool jedisPool;

    static {
        //配置连接池
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 连接池中最大的活动连接数
        poolConfig.setMaxTotal(100);
        // 连接池中最大的空闲连接数
        poolConfig.setMaxIdle(50);
        // 连接池中最小的空闲连接数
        poolConfig.setMinIdle(10);
        // 获取连接时的最大等待毫秒数
        poolConfig.setMaxWaitMillis(20000);
        //创建连接池对象 dev
//        jedisPool = new JedisPool(poolConfig,
//                "127.0.0.1",
//                5379,
//                1000);
//        jedisPool = new JedisPool(poolConfig,
//                "127.0.0.1",
//                6379,
//                1000);
        //创建连接池对象 prod
        jedisPool = new JedisPool(poolConfig,
                "172.31.0.2",
                5379,
                5000);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

}
