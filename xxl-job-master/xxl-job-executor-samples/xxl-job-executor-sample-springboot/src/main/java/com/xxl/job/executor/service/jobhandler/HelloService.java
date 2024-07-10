package com.xxl.job.executor.service.jobhandler;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wing
 * @create 2024/7/9
 */
@Service
public class HelloService {

    public void methodA() {
        System.out.println("执行任务methodA:" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
    }
    public void methodB() {
        System.out.println("执行任务methodB:" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
    }
}
