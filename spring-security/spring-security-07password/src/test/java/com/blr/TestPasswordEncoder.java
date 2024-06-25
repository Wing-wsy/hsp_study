package com.blr;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPasswordEncoder {


    public static void main(String[] args) {

        //1.加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
        // $2a$10$p8raQl.TIivKc8OjyOQPQO7EJQcu2fxPs8neZTJBT7GM9JPC0nK.O

    }
}
