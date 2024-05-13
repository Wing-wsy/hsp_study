package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    /* 查询全部 */
    @Test
    public void testSelectList() {
        //selectList()根据MP内置的条件构造器查询一个list集合，null表示没有条件，即查询所有
        userMapper.selectList(null).forEach(System.out::println);
    }

    /* 新增记录 */
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("张三");
        user.setAge(23);
        user.setEmail("zhangsan@atguigu.com");
        //INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        int result = userMapper.insert(user);
        System.out.println("受影响行数:"+result);
        //1475754982694199298 雪花算法自动生成的ID
        System.out.println("id自动获取:"+user.getId());
    }

    /* 通过id删除记录 */
    @Test
    public void testDeleteById(){
        //通过id删除用户信息
        //DELETE FROM user WHERE id=?
        int result = userMapper.deleteById(1475754982694199298L);
        System.out.println("受影响行数:"+result);
    }

    /* 通过map条件删除记录 */
    @Test
    public void testDeleteByMap(){
        //根据map集合中所设置的条件删除记录
        //DELETE FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("age", 23);
        map.put("name", "张三");
        int result = userMapper.deleteByMap(map);
        System.out.println("受影响行数:"+result);
    }
    /* 通过id批量删除记录 */
    @Test
    public void testDeleteBatchIds(){
        //通过多个id批量删除
        //DELETE FROM user WHERE id IN ( ? , ? , ? )
        //存在逻辑删除标识 @TableLogic 后
        //UPDATE user SET is_deleted=1 WHERE id IN ( ? , ? , ? ) AND is_deleted=0
        List<Long> idList = Arrays.asList(4L, 5L, 3L);
        int result = userMapper.deleteBatchIds(idList);
        System.out.println("受影响行数:"+result);
    }

    /* 修改记录 */
    @Test
    public void testUpdateById(){
        User user = new User();
        user.setId(4L);
        user.setName("heihei");
        user.setAge(66);
        //UPDATE user SET name=?, age=? WHERE id=?
        int result = userMapper.updateById(user);
        System.out.println("受影响行数:"+result);
    }

    /* 根据id查询用户信息 */
    @Test
    public void testSelectById(){
        //根据id查询用户信息
        //SELECT id,name,age,email FROM user WHERE id=?
        User user = userMapper.selectById(4L);
        System.out.println(user);
    }

    /* 根据多个id查询多个用户信息 */
    @Test
    public void testSelectBatchIds(){
        //根据多个id查询多个用户信息
        //SELECT id,name,age,email FROM user WHERE id IN ( ? , ? )
        List<Long> idList = Arrays.asList(4L, 5L);
        List<User> list = userMapper.selectBatchIds(idList);
        list.forEach(System.out::println);
    }

    /*通过map条件查询用户信息*/
    @Test
    public void testSelectByMap(){
        //通过map条件查询用户信息
        //SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("age", 24);
        map.put("name", "Billie");
        List<User> list = userMapper.selectByMap(map);
        list.forEach(System.out::println);
    }

    /*自定义一个查询SQL*/
    @Test
    public void testSelectByMapByMe(){
        //通过map条件查询用户信息
        //SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        Map<String, Object> map  = userMapper.selectMapById(4L);
        System.out.println(map);
    }

}
