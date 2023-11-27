package com.hspedu.transformation;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author wing
 * @create 2023/11/27
 * 演示 OutputStreamWriter 使用
 * 把FileOutputStream 字节流，转成字符流 OutputStreamWriter
 */
public class OutputStreamWriter_ {

    public static void main(String[] args) {

    }

    @Test
    public void readFile01() throws IOException {
        String filePath = "hello.txt";
        String charSet = "utf-8";
//        String charSet = "gbk";
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath), charSet);
        osw.write("hi, 教育");
        osw.close();
        System.out.println("按照 " + charSet + " 保存文件成功~");
    }
}
