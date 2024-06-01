package com.qfedu.test;

import com.qfedu.config.ApplicationContextConfig;
import com.qfedu.dao.BookDAOImpl;
import com.qfedu.proxy.MyStaticProxy;
import com.qfedu.dao.StudentDAOImpl;
import com.qfedu.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        UserServiceImpl userService = (UserServiceImpl)context.getBean("userServiceImpl");
        System.out.println(userService);
        System.out.println(userService.getUserDao());
    }
}
