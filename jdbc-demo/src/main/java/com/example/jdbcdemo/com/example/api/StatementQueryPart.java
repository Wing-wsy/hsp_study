package com.example.jdbcdemo.com.example.api;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wing
 * @create 2024/4/25
 */
public class StatementQueryPart {
//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        /** 1.注册驱动
//         *    依赖：驱动版本 8+ ：com.mysql.cj.jdbc.Driver
//         *         驱动版本 5+ ：com.mysql.jdbc.Driver
//         */
//
//        // 方案一：看里面的源码其实重复注册了（mysql的驱动包里面已经在static代码块中进行了注册，这样不用我们自己再去注册了）
////         DriverManager.registerDriver(new Driver());
//
//        // 方案二：解决了重复注册，但是 new Driver() 固定用的是 com.mysql.jdbc.Driver;的包，将来换成oracle要修改源代码，也不好
//        // new Driver();
//
//        // 方案三：解决全部问题，将来可以将字符串从配置文件读取，所以即使切换数据库，不用修改源码，只需修改配置文件即可
//        // 其实所有方案的目的就是为了执行Driver类里的静态代码（注册驱动），选择下面的反射方式去加载类，加载类就会执行静态代码
////        Class.forName("com.mysql.jdbc.Driver");
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        // 2.获取连接  jdbc:数据库厂商名://ip地址:port/数据库名
//        Connection connection = DriverManager.getConnection(
//                "jdbc:mysql://47.76.68.216:3306/mybatis_plus",
//                "root",
//                "P@ssw0rd123!");
//        // 3.创建发送sql语句对象
//        Statement statement = connection.createStatement();
//        // 4.发送sql语句，并获取返回结果
//        String sql = "select name,age from user;";
//        ResultSet resultSet = statement.executeQuery(sql);
//        // 5.结果集解析
//        while (resultSet.next()) {
//            String name = resultSet.getString("name");
//            int age = resultSet.getInt("age");
//            System.out.println(name + " - " + age);
//        }
//        // 6.资源关闭
//        resultSet.close();
//        statement.close();
//        connection.close();
//    }

//    public static void main(String[] args) throws Exception {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection = DriverManager.getConnection(
//                "jdbc:mysql://47.76.68.216:3306/mybatis_plus",
//                "root",
//                "P@ssw0rd123!");
//        // 4.发送sql语句，并获取返回结果
//        String sql = "insert into goods(name)values(?)";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        long start = System.currentTimeMillis();
//        for(int i = 1;i <= 1000;i++){
//            ps.setString(1, "name_" + i);
//            ps.executeUpdate();
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("花费的时间为：" + (end - start)); // 136825
//        // 6.资源关闭
//        ps.close();
//        connection.close();
//    }
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://47.76.68.216:3306/mybatis_plus?rewriteBatchedStatements=true",
                "root",
                "P@ssw0rd123!");

        //1.设置为不自动提交数据
        connection.setAutoCommit(false);

        // 4.发送sql语句，并获取返回结果
        String sql = "insert into goods(name)values(?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        long start = System.currentTimeMillis();
        for(int i = 1;i <= 1000;i++){
            ps.setString(1, "name_" + i);
            //1.“攒”sql
            ps.addBatch();
            if(i % 200 == 0){
                //2.执行
                ps.executeBatch();
                //3.清空
                ps.clearBatch();
            }
        }

        //2.提交数据
        connection.commit();
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为：" + (end - start)); // 136825
        // 6.资源关闭
        ps.close();
        connection.close();
    }
}
