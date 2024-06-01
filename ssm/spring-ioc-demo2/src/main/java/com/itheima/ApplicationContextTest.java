package com.itheima;

import com.itheima.beans.OtherBean;
import com.itheima.config.ApplicationContextConfig;
import com.itheima.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wing
 * @create 2024/6/1
 */
public class ApplicationContextTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
//        UserService bean = annotationConfigApplicationContext.getBean(UserService.class);
        OtherBean bean = annotationConfigApplicationContext.getBean(OtherBean.class);
        System.out.println(bean);

    }
}
