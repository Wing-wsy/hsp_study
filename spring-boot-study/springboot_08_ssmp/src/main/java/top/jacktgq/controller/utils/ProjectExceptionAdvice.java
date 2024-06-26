package top.jacktgq.controller.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author CandyWall
 * @Date 2022/1/20--15:38
 * @Description 作为springmvc的全局异常处理器，下面还可以根据不同的异常执行不同的处理，这里就不区分直接Exception全捕获统一处理
 */
//@ControllerAdvice
@RestControllerAdvice
public class ProjectExceptionAdvice {
    // 拦截所有的异常信息
    @ExceptionHandler
    public R doException(Exception ex) {
        // 记录日志
        // 通知运维
        // 通知开发
        ex.printStackTrace();
        return new R("服务器故障，请稍后再试");
    }
}
