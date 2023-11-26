package com.hspedu.outputstream_;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author wing
 * @create 2023/11/26
 * 演示使用 FileOutputStream 将数据写到文件中,
 * 如果该文件不存在，则创建该文件
 */
public class FileOutputStream01 {

    public static void main(String[] args) {

    }

    /**
     * 演示使用 FileOutputStream 将数据写到文件中,
     * 如果该文件不存在，则创建该文件
     */
    @Test
    public void writeFile() {

        //创建 FileOutputStream对象
        String filePath = "a.txt";
        FileOutputStream fileOutputStream = null;
        try {
            //得到 FileOutputStream对象 对象
            //老师说明
            //1. new FileOutputStream(filePath) 创建方式，当写入内容时，会覆盖原来的内容
            //2. new FileOutputStream(filePath, true) 创建方式，当写入内容是，是追加到文件后面
            fileOutputStream = new FileOutputStream(filePath, true);
            //1、写入一个字节
//            fileOutputStream.write('H');

            //2、写入字符串
            String str = "hello,world!";
            //str.getBytes() 可以把 字符串-> 字节数组
//            fileOutputStream.write(str.getBytes());

            fileOutputStream.write(str.getBytes(), 0, 3);

//            for (int i = 0; i < 2; i++) {
//                fileOutputStream.write(str.getBytes(), 0, 3);
//            }
//            write(byte[] b, int off, int len) 将 len字节从位于偏移量 off的指定字节数组写入此文件输出流


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
