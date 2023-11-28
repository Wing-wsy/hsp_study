package com.hspedu.properties_;

import org.junit.Test;

import java.io.*;

/**
 * @author wing
 * @create 2023/11/28
 * 传统方式读取配置文件
 */
public class Properties01 {

    @Test
    public void readFile01() throws IOException {
        //读取mysql.properties 文件，并得到ip, user 和 pwd
        BufferedReader br = new BufferedReader(new FileReader("mysql.properties"));
        String line = "";
        while ((line = br.readLine()) != null) { //循环读取
            String[] split = line.split("=");
            //如果我们要求指定的ip值
            if("pwd".equals(split[0])) {
                System.out.println(split[0] + "值是: " + split[1]);
            }
        }
        br.close();
    }
}
