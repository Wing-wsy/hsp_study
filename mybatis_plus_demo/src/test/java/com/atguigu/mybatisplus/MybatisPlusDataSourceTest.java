package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.service.ProductService;
import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisPlusDataSourceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    /* 查询全部 */
    @Test
    public void testDynamicDataSource(){
        System.out.println(userService.getById(4L));
        System.out.println(productService.getById(1L));
    }


}
