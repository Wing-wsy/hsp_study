package com.hspedu.file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author wing
 * @create 2023/11/26
 * 对文件的一些操作
 */
public class Directory_ {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        System.out.println(t1.getState());
        t1.start();
        System.out.println(t1.getState());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(t1.getState());
    }


    // 判断news1.txt是否存在，存在则删除
    @Test
    public void m1(){
        File file = new File("news1.txt");
        if(file.exists()){
            if (file.delete()) {
                System.out.println(file + " 删除成功");
            }else {
                System.out.println("删除失败");
            }
        }else{
            System.out.println("文件不存在");
        }
    }

    // 判断目录是否存在，存在则删除（Java里目录也是一个特殊的文件）
    @Test
    public void m2(){
        File file = new File("fileTest");
        if(file.exists()){
            if (file.delete()) {
                System.out.println(file + " 删除成功");
            }else {
                System.out.println("删除失败");
            }
        }else{
            System.out.println("目录不存在");
        }
    }

    // 判断目录是否存在，存在则提示存在，否则创建目录
    @Test
    public void m3(){
        File file = new File("fileTest");
        if(file.exists()){
            System.out.println("目录已存在");
        }else{
            // mkdir创建目录，mkdirs创建多级目录
            if (file.mkdir()) {
                System.out.println("创建成功");
            }else{
                System.out.println("创建失败");
            }
        }
    }
}
