package com.qfedu.dao;

import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl implements UserDAO{

    public void insert(String str){
        System.out.println("user-----insert" + "," + str);
    }
}
