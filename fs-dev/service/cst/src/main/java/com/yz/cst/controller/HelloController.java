package com.yz.cst.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Slf4j
@RestController
@RequestMapping("h")
public class HelloController {

    @GetMapping("hello")
    public Object hello() {
        log.info("info!~~~~");
        int i = 1/0;
        return "Hello world~";
    }
}
