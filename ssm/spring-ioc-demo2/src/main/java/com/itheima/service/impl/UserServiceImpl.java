package com.itheima.service.impl;


import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> listUsers() {
        List<User> users = userMapper.queryUsers();
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }

}
