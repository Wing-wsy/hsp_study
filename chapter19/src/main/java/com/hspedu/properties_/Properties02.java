package com.hspedu.properties_;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

/**
 * @author wing
 * @create 2023/11/28
 * 使用Properties类完成对mysql.properties的读取
 */
public class Properties02 {

    @Test
    public void readFile01() throws IOException {
        //使用Properties 类来读取mysql.properties 文件

        //1. 创建Properties 对象
        Properties properties = new Properties();
        //2. 加载指定配置文件
        properties.load(new FileReader("mysql.properties"));
        //3. 把k-v显示控制台
        properties.list(System.out);
        //4. 根据key 获取对应的值
        String user = properties.getProperty("user");
        String pwd = properties.getProperty("pwd");
        System.out.println("用户名=" + user);
        System.out.println("密码是=" + pwd);
    }
}
