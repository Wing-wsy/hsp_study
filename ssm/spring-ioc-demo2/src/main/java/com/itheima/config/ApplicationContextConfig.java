package com.itheima.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.itheima.anno.MyMapperScan;
import com.itheima.imports.MyImportBeanDefinitionRegistrar;
import com.itheima.imports.MyImportSelector;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * @author wing
 * @create 2024/6/1
 */
@Configuration
@ComponentScan("com.itheima")
@MapperScan("com.itheima.mapper")
//@Import(MyImportSelector.class)
//@Import(MyImportBeanDefinitionRegistrar.class)
@MyMapperScan
public class ApplicationContextConfig {

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://47.76.68.216:3306/mybatis_plus?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("P@ssw0rd123!");
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }
}
