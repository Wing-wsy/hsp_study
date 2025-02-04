package com.example.hxds.bff.driver.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.json.JSONObject;
import com.example.hxds.common.exception.HxdsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        JSONObject json = new JSONObject();
        //处理后端验证失败产生的异常
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            json.set("error", exception.getBindingResult().getFieldError().getDefaultMessage());
        }
        //HTTP请求类型不正确的异常
        else if (e instanceof HttpRequestMethodNotSupportedException) {
            json.set("error", "Web方法不支持当前的请求类型");
        }
        //缺少必须提交的表单数据
        else if (e instanceof HttpMessageNotReadableException) {
            json.set("error", "缺少提交的数据");
        } 
        //处理业务异常
        else if (e instanceof HxdsException) {
            log.error("执行异常", e);
            HxdsException exception = (HxdsException) e;
            json.set("error", exception.getMsg());
        }
        //司机已经注册异常
        else if (e.getMessage().contains("该微信无法注册")) {
            log.error("执行异常", e);
            json.set("error", "该微信无法注册");
        }
        //司机登陆时候的手机号不一致
        else if (e.getMessage().contains("当前手机号与注册手机号不一致")) {
            log.error("执行异常", e);
            json.set("error", "当前手机号与注册手机号不一致");
        }
        //处理其余的异常
        else {
            log.error("执行异常", e);
            json.set("error", "执行异常");
        }
        return json.toString();
    }

    /**
     * 捕获并处理客户端未登录或者未获取授权的异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public String unLoginHandler(Exception e) {
        JSONObject json = new JSONObject();
        json.set("error", e.getMessage());
        return json.toString();
    }

}
