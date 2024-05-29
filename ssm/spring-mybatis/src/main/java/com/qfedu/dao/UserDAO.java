package com.qfedu.dao;

import com.qfedu.pojo.User;
import org.springframework.stereotype.Component;

import java.util.List;


// 这里不用使用@Component,因为MapperScannerConfigurer会扫描指定包下所有的dao

public interface UserDAO {

    public List<User> queryUsers();

    public int insertUser(User user);

}
