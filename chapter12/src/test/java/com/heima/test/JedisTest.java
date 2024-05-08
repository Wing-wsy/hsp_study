//package com.heima.test;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import redis.clients.jedis.Jedis;
//
///**
// * @author wing
// * @create 2024/4/24
// */
//public class JedisTest {
//
//    private Jedis jedis;
//
//    @BeforeEach
//    void setUp() {
//        // 1.建立连接
////        jedis = new Jedis("101.34.235.45",6379);
//        jedis = JedisConnectionFactory.getJedis();
//        // 2.设置密码
////        jedis.auth("123456");
//        // 3.选择库
//        jedis.select(0);
//    }
//    @Test
//    void testString() {
////        jedis.set("name","hei11");
//        String s = jedis.get("hobby");
//        System.out.println(s);
//    }
//    @AfterEach
//    void afterAll() {
//        if (jedis != null) {
//            jedis.close();
//        }
//    }
//}
