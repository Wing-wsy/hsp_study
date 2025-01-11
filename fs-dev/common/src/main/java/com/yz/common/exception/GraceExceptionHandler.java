package com.yz.common.exception;

import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局兜底异常处理器
 *
 * 其他服务若没有配置异常处理器，则使用该处理器
 * 若配置了异常处理器，则使用配置的处理器
 * 正常使用该处理器即可
 */
@Slf4j
@ControllerAdvice
public class GraceExceptionHandler {

    // 处理系统自定义异常
    @ExceptionHandler(MyCustomException.class)
    @ResponseBody
    public GraceResult returnMyCustomException(MyCustomException e) {
        log.error("业务异常：", e);
        return GraceResult.exception(ResponseStatusEnum.SYSTEM_ERROR_GRACE);
    }

    // 处理异常兜底
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public GraceResult returnException(Exception e) {
        log.error("系统异常：", e);
        return GraceResult.exception(ResponseStatusEnum.SYSTEM_ERROR_GRACE);
    }


//    public Map<String, String> getErrors(BindingResult result) {
//
//        Map<String, String> map = new HashMap<>();
//
//        List<FieldError> errorList = result.getFieldErrors();
//        for (FieldError fe : errorList) {
//            // 错误所对应的属性字段名
//            String field = fe.getField();
//            // 错误信息
//            String message = fe.getDefaultMessage();
//
//            map.put(field, message);
//        }
//
//        return map;
//    }

}
