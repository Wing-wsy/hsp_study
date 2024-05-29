package com.qfedu.test;

import com.qfedu.dao.JDKDynamicProxy;
import com.qfedu.dao.UserDAO;
import com.qfedu.dao.UserDAOImpl;

/**
 * @author wing
 * @create 2024/5/27
 */
public class Test1 {

    public static void main(String[] args) {
        UserDAOImpl u = new UserDAOImpl();
        JDKDynamicProxy jdkDynamicProxy = new JDKDynamicProxy(u);
        UserDAO proxy = (UserDAO)jdkDynamicProxy.getProxy();
        proxy.insert("hello22222");
    }
}
