package com.yz.common.exception;

import com.yz.common.result.ResponseStatusEnum;

/**
 * 优雅的处理异常，统一进行封装
 */
public class GraceException {

    public static void display(ResponseStatusEnum statusEnum) {
        throw new MyCustomException(statusEnum);
    }

    public static void displayCustom(String code) {
        ResponseStatusEnum responseStatusEnum = ResponseStatusEnum.get(code);
        display(responseStatusEnum);
    }

}
