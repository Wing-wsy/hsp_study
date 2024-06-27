package com.blr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/admin")  //ADMIN 角色
    public String admin() {
        return "admin ok";
    }

    @GetMapping("/user")  //USER 角色
    public String user() {
        return "user ok";
    }

    @GetMapping("/getInfo")  //READ_INFO 权限
    public String getInfo() {
        return "info ok";
    }

}