package com.yz.common.result;

import com.yz.common.constant.Strings;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.MDC;

import java.util.Map;

/**
 * 自定义响应数据类型枚举
 *
 * @Description: 自定义响应数据结构
 * 				本类可提供给 H5/ios/安卓/公众号/小程序 使用
 * 				前端接受此类数据（json object)后，可自行根据业务去实现相关功能
 */
@Schema(description = "响应结果模型")
public class GraceResult {

    @Schema(description = "响应业务状态码：200成功 其他状态码均为失败")
    private Integer status;

    @Schema(description = "响应消息（响应非200状态时，msg为错误原因）")
    private String msg;

    @Schema(description = "响应状态码（响应非200状态时，code为错误编码）")
    private String code;

    @Schema(description = "是否请求成功（true 或者 false，status=200时，对应这里的值为true，否则为false）")
    private Boolean success;

    @Schema(description = "响应数据（响应200状态时，成功数据包装在data中）")
    private Object data;

    @Schema(description = "当前请求的日志ID，接口出错可提供给后端进行排查")
    private String id;

    /**
     * 成功返回，带有数据的，直接往OK方法丢data数据即可
     * @param data
     * @return
     */
    public static GraceResult ok(Object data) {
        return new GraceResult(data);
    }
    /**
     * 成功返回，不带有数据的，直接调用ok方法，data无须传入（其实就是null）
     * @return
     */
    public static GraceResult ok() {
        return new GraceResult(ResponseStatusEnum.SUCCESS);
    }
    public GraceResult(Object data) {
        this.status = ResponseStatusEnum.SUCCESS.status();
        this.msg = ResponseStatusEnum.SUCCESS.msg();
        this.code = ResponseStatusEnum.SUCCESS.name();
        this.success = ResponseStatusEnum.SUCCESS.success();
        this.data = data;
        this.id = MDC.get(Strings.TRACE_ID);
    }


    /**
     * 错误返回，直接调用error方法即可，当然也可以在ResponseStatusEnum中自定义错误后再返回也都可以
     * @return
     */
    public static GraceResult error() {
        return new GraceResult(ResponseStatusEnum.FAILED);
    }

    /**
     * 错误返回，map中包含了多条错误信息，可以用于表单验证，把错误统一的全部返回出去
     * @param map
     * @return
     */
    public static GraceResult errorMap(Map map) {
        return new GraceResult(ResponseStatusEnum.FAILED, map);
    }

    /**
     * 错误返回，直接返回错误的消息
     * @param msg
     * @return
     */
    public static GraceResult errorMsg(String msg) {
        return new GraceResult(ResponseStatusEnum.FAILED, msg);
    }

    /**
     * 参数不通过异常
     * @param errorArgument 不通过的参数
     * @return
     */
    public static GraceResult errorArgumentNotValid(String errorArgument) {
        return new GraceResult(ResponseStatusEnum.ARGUMENT_NOT_VALID, errorArgument);
    }

    /**
     * 错误返回，token异常，一些通用的可以在这里统一定义
     * @return
     */
    public static GraceResult errorTicket() {
        return new GraceResult(ResponseStatusEnum.TICKET_INVALID);
    }

    /**
     * 自定义错误范围，需要传入一个自定义的枚举，可以到[ResponseStatusEnum.java[中自定义后再传入
     * @param responseStatus
     * @return
     */
    public static GraceResult errorCustom(ResponseStatusEnum responseStatus) {
        return new GraceResult(responseStatus);
    }
    public static GraceResult exception(ResponseStatusEnum responseStatus) {
        return new GraceResult(responseStatus);
    }

    public static GraceResult exception() {
        return GraceResult.error();
    }

    public GraceResult(ResponseStatusEnum responseStatus) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.code = responseStatus.name();
        this.success = responseStatus.success();
        this.id = MDC.get(Strings.TRACE_ID);
    }
    public GraceResult(ResponseStatusEnum responseStatus, Object data) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.code = responseStatus.name();
        this.success = responseStatus.success();
        this.data = data;
        this.id = MDC.get(Strings.TRACE_ID);
    }
    public GraceResult(ResponseStatusEnum responseStatus, String msg) {
        this.status = responseStatus.status();
        this.msg = msg;
        this.code = responseStatus.name();
        this.success = responseStatus.success();
        this.id = MDC.get(Strings.TRACE_ID);
    }

    public GraceResult() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
