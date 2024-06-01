package com.qfedu.service;

import com.qfedu.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wing
 * @create 2024/5/31
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }
}
