package com.example.redisdemo;

import com.example.redisdemo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testString() {
        //写入一条String数据
        redisTemplate.opsForValue().set("name","oooooo1");
        //获取String数据
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);

    }

    @Test
    void testSaveUser() {
        //写入数据
        redisTemplate.opsForValue().set("user:100",new User("huge",17));
        //获取数据
        User user = (User)redisTemplate.opsForValue().get("user:100");
        System.out.println(user);
    }

}
