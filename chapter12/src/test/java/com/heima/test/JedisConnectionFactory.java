//package com.heima.test;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * @author wing
// * @create 2024/4/24
// */
//public class JedisConnectionFactory {
//    private static final JedisPool jedisPool;
//    static {
//        //配置连接池
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        // 最大连接
//        jedisPoolConfig.setMaxTotal(8);
//        // 最大空闲连接（空闲时最多预备连接数）
//        jedisPoolConfig.setMaxIdle(8);
//        // 最小空闲连接
//        jedisPoolConfig.setMinIdle(0);
//        // 没有连接可用时，最多等待1000毫秒，过了还没空余连接可用就报错
//        jedisPoolConfig.setMaxWaitMillis(1000);
//        //创建连接池对象
//        jedisPool = new JedisPool(jedisPoolConfig,"47.76.68.216",6379,1000,"123456abc");
//    }
//    public static Jedis getJedis(){
//        return jedisPool.getResource();
//    }
//}