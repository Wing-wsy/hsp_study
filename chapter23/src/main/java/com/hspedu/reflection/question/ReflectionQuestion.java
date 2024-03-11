package src.main.java.com.hspedu.reflection.question;


import src.main.java.com.hspedu.Cat;
import sun.applet.AppletClassLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author wing
 * @create 2023/11/26
 */
public class ReflectionQuestion {

    public static void main(String[] args) throws IOException {
        //问题：根据配置文件re.properties指定信息，创建Cat对象并调用方法hi

        //1、传统方式 很容易实现
        Cat cat = new Cat();

//        cat.hi();
//
//        //2、反射来实现
//        // 先使用 Properties 类来读取配置文件的信息
//        Properties properties = new Properties();
////        properties.load(new FileInputStream("src\\main\\resources\\re.properties"));
//
//
//        properties.load(new InputStreamReader(
//                Files.newInputStream(Paths.get("src\\main\\resources\\re.properties"))));
//
//        String classfullpath = properties.get("classfullpath").toString(); // com.hspedu.Cat
//        String method = properties.get("method").toString(); // hi
//
//        System.out.println(classfullpath);
//        System.out.println(method);



    }
}
