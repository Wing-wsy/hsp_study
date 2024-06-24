package com.baizhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    /**
     * 跳转到/resources/templates/login.html 页面
     */
    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }

}
