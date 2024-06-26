package com.blr.controller;

import com.blr.entity.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class AuthorizeMethodController {

    @PreAuthorize("hasRole('ADMIN')  and authentication.name =='root'")  //限制角色是ADMIN，并且用户名是root才能访问
//    @PreAuthorize("hasAuthority('READ_INFO')")
    @GetMapping
    public String hello() {
        return "hello";
    }

    // 传进来的name参数与当前登录用户名比对，相同才允许访问
    @PreAuthorize("authentication.name==#name")
    @GetMapping("/name")
    public String hello(String name) {
        return "hello:" + name;
    }

    // 过滤传进来的集合，与2不整除的才要
    @PreFilter(value = "filterObject.id%2!=0",filterTarget = "users") //filterTarget 必须是 数组  集合类型
    @PostMapping("/users")
    public void addUsers(@RequestBody List<User> users) {
        System.out.println("users = " + users);
    }

    // 方法返回前做一些处理,只返回 id = 1的数据
    @PostAuthorize("returnObject.id==1")
    @GetMapping("/userId")
    public User getUserById(Integer id) {
        System.out.println(id);
        return new User(id, "blr");
    }

    @PostFilter("filterObject.id%2==0")//用来对方法返回值进行过滤
    @GetMapping("/lists")
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User(i, "blr:" + i));
        }
        return users;
    }



    @Secured({"ROLE_USER"}) //只能判断角色
    @GetMapping("/secured")
    public User getUserByUsername() {
        return new User(99, "secured");
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"}) //具有其中一个即可
    @GetMapping("/username")
    public User getUserByUsername2(String username) {
        return new User(99, username);
    }


    @PermitAll
    @GetMapping("/permitAll")
    public String permitAll() {
        return "PermitAll";
    }

    @DenyAll
    @GetMapping("/denyAll")
    public String denyAll() {
        return "DenyAll";
    }

    @RolesAllowed({"ROLE_ADMIN","ROLE_USER"}) //具有其中一个角色即可
    @GetMapping("/rolesAllowed")
    public String rolesAllowed() {
        return "RolesAllowed";
    }
}
