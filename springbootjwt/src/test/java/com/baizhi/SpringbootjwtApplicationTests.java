package com.baizhi;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


class SpringbootjwtApplicationTests {

    /**
     * 获取令牌
     */
    @Test
    void contextLoads() {
        HashMap<String, Object> map = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,600);
        String token = JWT.create()
                //.withHeader(map)  //header 可以不写，那就是使用默认 HS256 加密
                .withClaim("userid",12)
                .withClaim("username", "xiaochen")//payload
                .withExpiresAt(instance.getTime())//指定令牌过期时间
                .sign(Algorithm.HMAC256("!Q@W#E$R"));//签名
        // token令牌：eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTkwNDIyMzEsInVzZXJpZCI6MTIsInVzZXJuYW1lIjoieGlhb2NoZW4ifQ.kOEQ2MUFmedZsf_qVqteZ63Wz0jVPeNwWP7TWA75HbI
        System.out.println(token);
    }

    /**
     * 验证令牌
     */
    @Test
    public void test(){
        //创建验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!Q@W#E$R")).build();

        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MTkwNDI5OTgsInVzZXJpZCI6MTIsInVzZXJuYW1lIjoieGlhb2NoZW4ifQ.wcp-hrLDyKe1c0Yrdl1FfyyGEBAxn3V7pq4_yl1gQVA");

        System.out.println(verify.getClaim("userid").asInt());
        System.out.println(verify.getClaim("username").asString());
        System.out.println("过期时间: "+verify.getExpiresAt());

        //System.out.println(verify.getClaims().get("userid").asInt());
        //System.out.println(verify.getClaims().get("username").asString());


    }
}
