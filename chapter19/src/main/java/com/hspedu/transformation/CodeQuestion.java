package com.hspedu.transformation;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author wing
 * @create 2023/11/27
 * 这里演示乱码，为引入转换流做铺垫，就是为什么使用转换流
 */
public class CodeQuestion {

    public static void main(String[] args) {
    }

    @Test
    public void readFile01()  throws IOException{

        // 需求：读取 story.txt 文件到程序
        // 思路
        // 1、创建字符输入流 BufferedReader[处理流]
        // 2、使用BufferedReader 对象读取 story.txt
        // 3、默认情况下，读取文件是按照 utf-8 编码读取
        // 说明：由于刚好story.txt这个文件是使用的 utf-8 的编码，碰巧这里执行不会乱码
        // 但是如果使用工具将story.txt改成gbk编码，那么这里执行就会显示乱码

        /** 所以假如 story.txt 使用gbk编码，这里就会显示乱码，那解决方式就是可以引入转换流处理 */
        String filePath = "story.txt";
        //创建bufferedReader
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String s = br.readLine();
        System.out.println("读取到内容=" + s);
        br.close();
    }
}
