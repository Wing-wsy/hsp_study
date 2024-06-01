package com.qfedu.dao;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author wing
 * @create 2024/5/30
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof UserDaoImpl) {
            System.out.println("BeanPostProcessor的Before方法执行");
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof UserDaoImpl) {
            System.out.println("BeanPostProcessor的After方法执行");
        }
        return bean;
    }
}
