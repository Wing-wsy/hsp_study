package com.example.redisdemo;

import com.example.redisdemo.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

@SpringBootTest
class RedisStringTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testString() {
        //写入一条String数据
        stringRedisTemplate.opsForValue().set("name","胡歌");
        //获取String数据
        Object name = stringRedisTemplate.opsForValue().get("name");
        System.out.println(name);

    }

    @Test
    void testSaveUser() throws JsonProcessingException {
        //创建对象
        User user = new User("网wang",17);
        // 1.创建一个Json序列化对象
        String json = mapper.writeValueAsString(user);
        //写入数据
        stringRedisTemplate.opsForValue().set("user:200",json);
        //获取数据
        String jsonUser = stringRedisTemplate.opsForValue().get("user:200");
        // 手动反序列化
        User user1 = mapper.readValue(jsonUser,User.class);
        System.out.println(user1);
    }

    @Test
    void testHash() throws JsonProcessingException {
        stringRedisTemplate.opsForHash().put("user:300","name","xiaoli");
        stringRedisTemplate.opsForHash().put("user:300","age","22");
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("user:300");
        System.out.println(entries);
    }

}
