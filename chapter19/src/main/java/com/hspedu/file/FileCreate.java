package com.hspedu.file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author wing
 * @create 2023/11/26
 * 演示创建文件
 */
public class FileCreate {

    public static void main(String[] args) {
    }

    // 方式 1：new File(String pathName)  根据路径构造一个File对象
    @Test
    public void create01(){
        // 路径名可以是相对路径或绝对路径，这里使用相对路径
        String filePath = "hello.txt";
        // 这里的file只是在内存中创建了对象，没有写入磁盘中
        File file = new File(filePath);
        try {
            // 这里才是真正将内存对象写入到磁盘中保存
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 方式 2：new File(File parent,String child)   根据父目录文件 + 子路径构建【在e盘中创建文件 news2.txt】
    @Test
    public void create02(){
        File fileParent = new File("e:\\");
        String filePath = "news2.txt";
        File file = new File(fileParent,filePath);
        try {
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 方式 3：new File(String parent,String child)  根据父目录 + 子路径构建
    @Test
    public void create03(){
        String fileParent = "e:\\";
        String filePath = "news2.txt";
        File file = new File(fileParent,filePath);
        try {
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
