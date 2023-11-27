package com.hspedu.transformation;

import org.junit.Test;
import java.io.*;
import java.io.IOException;


/**
 * @author wing
 * @create 2023/11/27
 * 将UTF-8编码的文本文件，转换为GBK编码的文本文件
 */
public class ConversionStreamTest {

    public static void main(String[] args) {

    }

    @Test
    public void readFile01() throws IOException {
        // 需求：读取UTF-8编码的文本文件 a.txt，然后使用 GBK编码输出到 b.txt
        // 1、定义转换流
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        try {
            //创建流对象,指定GBK编码
            isr = new InputStreamReader(new FileInputStream("hello.txt"),"UTF-8");
            osw = new OutputStreamWriter(new FileOutputStream("b.txt"),"GBK");

            int len;
            char[] buffer = new char[1024];
            while ((len=isr.read(buffer))!=-1){
                osw.write(buffer,0,len);
            }
            System.out.println("成功...");
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            if (osw!=null){
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr!=null){
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
