package com.hspedu.properties_;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

/**
 * @author wing
 * @create 2023/11/28
 * 使用Properties类添加key-val 到新文件mysql2.properties中
 * 使用Properties类完成对 mysq12.properties 的读取，并修改某个key-val
 */
public class Properties03 {

    @Test
    public void readFile01() throws IOException {
        //使用Properties 类来创建 配置文件, 修改配置文件内容

        Properties properties = new Properties();
        //创建
        //1. 如果该文件没有key 就是创建!!
        //2. 如果该文件有key ,就是修改!!
        properties.setProperty("charset", "utf8");
        properties.setProperty("user", "汤姆");//注意保存时，是中文的 unicode码值
        properties.setProperty("pwd", "888888");

        //将k-v 存储文件中即可
        properties.store(new FileOutputStream("mysql2.properties"), "This is commom");
        System.out.println("保存配置文件成功~");
    }
}

