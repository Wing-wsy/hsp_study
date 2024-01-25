package org.example.chapter02;

import sun.misc.Launcher;
import sun.security.ec.SunEC;
import sun.security.util.CurveDB;

import java.net.URL;
import java.security.Provider;

/**
 * @author wing
 * @create 2024/1/24
 */
public class ClassLoaderTest1 {
    public static void main(String[] args) {
//        System.out.println("********** 启动类加载器 ***********");
//        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
//        for (URL urL : urLs) {
//            System.out.println(urL.toExternalForm());
//        }
//
//        // 从 jsse.jar 选择一个类，来看看他的类加载器是什么
//        ClassLoader classLoader = Provider.class.getClassLoader();
//        System.out.println(classLoader); // null  说明是引导类加载器加载的
//
//        System.out.println("********** 扩展类加载器 ***********");
//        String extDirs = System.getProperty("java.ext.dirs");
//        for (String path : extDirs.split(";")) {
//            System.out.println(path);
//        }
//
//        // 从 sunec.jar 选择一个类，来看看他的类加载器是什么
//        ClassLoader classLoader1 = SunEC.class.getClassLoader();
//        System.out.println(classLoader1); // sun.misc.Launcher$ExtClassLoader@31befd9f  说明是扩展类加载器加载的

    }
}
