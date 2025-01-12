package com.yz.common.exception;


import com.yz.common.result.ResponseStatusEnum;

/**
 * 自定义异常
 * 目的：统一的处理异常信息
 */
public class MyCustomException extends RuntimeException {

    private ResponseStatusEnum responseStatusEnum;

    public MyCustomException(ResponseStatusEnum responseStatusEnum) {
        super("异常状态码为：" + responseStatusEnum.status() +
                ",异常信息为：" + responseStatusEnum.msg());
        this.responseStatusEnum = responseStatusEnum;
    }

    public ResponseStatusEnum getResponseStatusEnum() {
        return responseStatusEnum;
    }
    public void setResponseStatusEnum(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }
}
