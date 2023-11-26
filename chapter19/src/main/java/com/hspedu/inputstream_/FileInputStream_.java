package com.hspedu.inputstream_;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author wing
 * @create 2023/11/26
 * 演示FileInputStream的使用(字节输入流 文件--> 程序)
 */
public class FileInputStream_ {

    public static void main(String[] args) {

    }

    /**
     * 演示读取文件...
     * 单个字节的读取，效率比较低
     * -> 使用 read(byte[] b)
     */
    @Test
    public void readFile01() {
        String filePath = "hello.txt";
        int readData = 0;
        FileInputStream fileInputStream = null;
        try {
            //创建 FileInputStream 对象，用于读取文件
            fileInputStream = new FileInputStream(filePath);
            //从该输入流读取一个字节的数据。 如果没有输入可用，此方法将阻止。
            //如果返回-1 , 表示读取完毕
            while ((readData = fileInputStream.read()) != -1) {
//                System.out.print(readData);//
                // 如果文本中写入了中文，那么这里读取会乱码，因为一个汉字在utf-8中是使用3个字节来表示的，现在相当于将本来3个完整的字节拆成一个个读取，则必然乱码
                System.out.print((char)readData);//转成char显示
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流，释放资源.
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用 read(byte[] b) 读取文件，提高效率
     */
    @Test
    public void readFile02() {
        String filePath = "hello.txt";
        //字节数组
        byte[] buf = new byte[8]; //一次读取8个字节.
        int readLen = 0;
        FileInputStream fileInputStream = null;
        try {
            //创建 FileInputStream 对象，用于读取 文件
            fileInputStream = new FileInputStream(filePath);
            //从该输入流读取最多b.length字节的数据到字节数组。 此方法将阻塞，直到某些输入可用。
            //如果返回-1 , 表示读取完毕
            //如果读取正常, 返回实际读取的字节数，读取出来存放到buf数组中
            while ((readLen = fileInputStream.read(buf)) != -1) {
                System.out.print(new String(buf, 0, readLen));//显示
                // 可以研究下面这样会显示什么，对应视频28:00 https://www.bilibili.com/video/BV15B4y1u7Rn/?p=6&spm_id_from=pageDriver&vd_source=dc02a4c6e2a8e915fb8ee431999e5b2c
                // System.out.print(new String(buf, 0, readLen -1 ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流，释放资源.
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
