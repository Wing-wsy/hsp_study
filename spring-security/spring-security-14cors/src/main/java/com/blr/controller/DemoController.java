package com.blr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin //代表类中所有方法允许跨域  springmvc 注解解决方案 【处理跨域方式三】
public class DemoController {


    @GetMapping("/demo")

//     @CrossOrigin(origins = {"http://localhost:63342"}) //用来解决允许跨域访问注解 【处理跨域方式一】
//    @CrossOrigin   //直接写注解默认允许所有域 【处理跨域方式二】
    public String demo() {
        System.out.println("demo ok!");
        return "demo ok!";
    }
}
