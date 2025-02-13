package com.yz.base.bff.exception;

import com.yz.common.exception.MyCustomException;
import com.yz.common.result.GraceResult;
import com.yz.common.result.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bff服务全局兜底异常处理器
 */
@Slf4j
@ControllerAdvice
public class GraceExceptionHandlerBff {

    // 处理系统自定义异常
    @ResponseBody
    @ExceptionHandler(MyCustomException.class)
    public GraceResult returnMyCustomException(MyCustomException e) {
        log.error("业务异常：", e);
        return GraceResult.exception(e.getResponseStatusEnum());
    }

    // 处理参数校验异常
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GraceResult returnNotValidException(MethodArgumentNotValidException e) {
        // 出错的方法全路径名
        String method = e.getParameter().getMethod().toString();
        BindingResult result = e.getBindingResult();
        Map<String, String> errors = getErrors(result);
        String errorKey = "";
        String errorVal = "";
        for (String key : errors.keySet()) {
            String val = errors.get(key);
            errorKey = key;
            errorVal = val;
            break;
        }
        log.error("校验异常：{} {} 【{}】", errorKey, errorVal, method);
        return GraceResult.errorMsg(errorVal);
    }

    // 处理异常兜底
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public GraceResult returnException(Exception e) {
        log.error("系统异常：", e);
        return GraceResult.exception(ResponseStatusEnum.SYSTEM_ERROR_GRACE);
    }

    public Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();

        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError fe : errorList) {
            // 错误所对应的属性字段名
            String field = fe.getField();
            // 错误信息
            String message = fe.getDefaultMessage();
            map.put(field, message);
        }
        return map;
    }
}
