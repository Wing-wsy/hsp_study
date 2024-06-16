package com.hmdp.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Blog;
import com.hmdp.entity.User;
import com.hmdp.service.IBlogService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.SystemConstants;
import com.hmdp.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private IBlogService blogService;
    @Resource
    private IUserService userService;

    @PostMapping
    public Result saveBlog(@RequestBody Blog blog) {
        return blogService.saveBlog(blog);
    }

    @PutMapping("/like/{id}")
    public Result likeBlog(@PathVariable("id") Long id) {
        return blogService.likeBlog(id);
    }

    @GetMapping("/of/me")
    public Result queryMyBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .eq("user_id", user.getId()).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        // 这里还可以获取总记录数、总页数等等
        return Result.ok(records);
    }

    @GetMapping("/hot")
    public Result queryHotBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        return blogService.queryHotBlog(current);
    }

    @GetMapping("/{id}")
    public Result queryBlogById(@PathVariable("id") Long id) {
        return blogService.queryBlogById(id);
    }

    @GetMapping("/likes/{id}")
    public Result queryBlogLikes(@PathVariable("id") Long id) {
        return blogService.queryBlogLikes(id);
    }

    @GetMapping("/of/user")
    public Result queryBlogByUserId(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam("id") Long id) {
        // 根据用户查询
        // current：第几页； SystemConstants.MAX_PAGE_SIZE：每页的记录数
        Page<Blog> page = blogService.query()
                .eq("user_id", id).page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        return Result.ok(records);
    }

    @GetMapping("/of/follow")
    public Result queryBlogOfFollow(
            @RequestParam("lastId") Long max, @RequestParam(value = "offset", defaultValue = "0") Integer offset){
        return blogService.queryBlogOfFollow(max, offset);
    }

    /**  下面是异步线程池使用总结 */

    // 1) 使用默认线程池（注意：将自定义AsyncConfiguration类注释）
    @GetMapping("/testTask1")
    public Result testTask(){
        log.debug("testTaskController1，{}", "start");
        blogService.testTask1();
        log.debug("testTaskController1，{}", "end");
        return null;
    }

    // 2) 使用自定义线程池代替默认线程池（注意：实现自定义AsyncConfiguration类实现AsyncConfigurer接口）
    @GetMapping("/testTask2")
    public Result testTask2(){
        log.debug("testTaskController2，{}", "start");
        blogService.testTask2();
        log.debug("testTaskController2，{}", "end");
        return null;
    }

    // 3) 使用自定义线程池并指定名称（注意：AsyncTaskConfig类定义了两个线程池）
    @GetMapping("/testTask3")
    public Result testTask3(){
        log.debug("testTaskController3，{}", "start");
        blogService.testTask3();
        log.debug("testTaskController3，{}", "end");
        return null;
    }


    // 4) 带返回结果的线程池（默认线程池和自定义线程池均支持）
    @GetMapping("/testTask4")
    public Result testTask4(){
        log.debug("testTaskController4，{}", "start");
        Future<String> stringFuture = blogService.testTask4();
        try {
            log.debug("执行主线程其他业务逻辑。。。");
            log.debug("stringFuture==,{}",stringFuture.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.debug("testTaskController4，{}", "end");
        return null;
    }







}
