package com.example.jdbcdemo.com.example.api;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wing
 * @create 2024/4/25
 */
public class StatementQueryPart {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        /** 1.注册驱动
         *    依赖：驱动版本 8+ ：com.mysql.cj.jdbc.Driver
         *         驱动版本 5+ ：com.mysql.jdbc.Driver
         */

        // 方案一：看里面的源码其实重复注册了
        // DriverManager.registerDriver(new Driver());

        // 方案二：解决了重复注册，但是 new Driver() 固定用的是 com.mysql.jdbc.Driver;的包，将来换成oracle要修改源代码，也不好
        // new Driver();

        // 方案三：解决全部问题，将来可以将字符串从配置文件读取，所以即使切换数据库，不用修改源码，只需修改配置文件即可
        // 其实所有方案的目的就是为了执行Driver类里的静态代码（注册驱动），选择下面的反射方式去加载类，加载类就会执行静态代码
        Class.forName("com.mysql.jdbc.Driver");
        // 2.获取连接  jdbc:数据库厂商名://ip地址:port/数据库名
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.245:3306/y_mexico_system", "shaoyou", "Ycs11g8ap_BkQp#35fc&Xin7f");
        // 3.创建发送sql语句对象
        Statement statement = connection.createStatement();
        // 4.发送sql语句，并获取返回结果
        String sql = "select * from t_loan_order;";
        ResultSet resultSet = statement.executeQuery(sql);
        // 5.结果集解析
        while (resultSet.next()) {
            int loanId = resultSet.getInt("loan_id");
            String mobile = resultSet.getString("mobile");
            System.out.println(loanId + " - " + mobile);
        }
        // 6.资源关闭
        resultSet.close();
        statement.close();
        connection.close();
    }
}
