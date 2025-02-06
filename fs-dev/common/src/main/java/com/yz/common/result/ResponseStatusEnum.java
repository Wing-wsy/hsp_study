package com.yz.common.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 响应结果枚举，用于提供给GraceResult返回给前端
 * 【这里的中文提示信息仅开发人员方便查看，真实提示信息配置在数据库】
 */
public enum ResponseStatusEnum {

    SUCCESS(200, true, "操作成功！"),
    FAILED(500, false, "操作失败！"),

    SYSTEM_ERROR(500, false, "系统出现异常，请联系管理员！"),
    SYSTEM_ERROR_GRACE(500, false, "系统繁忙，请稍后再试！"),

    // 50x
    UN_LOGIN(501,false,"请登录后再继续操作！"),
    TICKET_INVALID(502,false,"会话失效，请重新登录！"),
    USER_ALREADY_EXIST_ERROR(5021,false,"该用户已存在，不可重复注册！"),
    USER_ISNOT_EXIST_ERROR(5023,false,"该用户不存在，请前往注册！"),
    WECHAT_NUM_ALREADY_MODIFIED_ERROR(5024,false,"用户昵称已被修改，请等待1年后再修改！"),

    NO_AUTH(503,false,"您的权限不足，无法继续操作！"),
    MOBILE_ERROR(504,false,"短信发送失败，请稍后重试！"),
    SMS_NEED_WAIT_ERROR(505,false,"短信发送太频繁~请稍后再试！"),
    SMS_CODE_ERROR(506,false,"验证码过期或不匹配，请稍后再试！"),
    USER_FROZEN(507,false,"用户已被冻结，请联系管理员！"),
    USER_UPDATE_ERROR(508,false,"用户信息更新失败，请联系管理员！"),
    USER_INACTIVE_ERROR(509,false,"请前往[账号设置]修改信息激活后再进行后续操作！"),
    USER_INFO_UPDATED_ERROR(5091,false,"用户信息修改失败！"),
    USER_INFO_UPDATED_NICKNAME_EXIST_ERROR(5092,false,"昵称已经存在！"),

    // 文件异常 51x
    FILE_UPLOAD_NULL_ERROR(510,false,"文件不能为空，请选择一个文件再上传！"),
    FILE_UPLOAD_FAILD(511,false,"文件上传失败！"),
    FILE_FORMATTER_FAILD(512,false,"文件图片格式不支持！"),
    FILE_MAX_SIZE_500KB_ERROR(5131,false,"仅支持500kb大小以下的文件上传！"),
    FILE_MAX_SIZE_2MB_ERROR(5132,false,"仅支持2MB大小以下的文件上传！"),
    FILE_NOT_EXIST_ERROR(514,false,"你所查看的文件不存在！"),

    // 自定义系统级别异常 54x
    SYSTEM_INDEX_OUT_OF_BOUNDS(541, false, "系统错误，数组越界！"),
    SYSTEM_ARITHMETIC_BY_ZERO(542, false, "系统错误，无法除零！"),
    SYSTEM_NULL_POINTER(543, false, "系统错误，空指针！"),
    SYSTEM_NUMBER_FORMAT(544, false, "系统错误，数字转换异常！"),
    SYSTEM_PARSE(545, false, "系统错误，解析异常！"),
    SYSTEM_IO(546, false, "系统错误，IO输入输出异常！"),
    SYSTEM_FILE_NOT_FOUND(547, false, "系统错误，文件未找到！"),
    SYSTEM_CLASS_CAST(548, false, "系统错误，类型强制转换错误！"),
    SYSTEM_PARSER_ERROR(549, false, "系统错误，解析出错！"),
    SYSTEM_DATE_PARSER_ERROR(550, false, "系统错误，日期解析出错！"),
    SYSTEM_NO_EXPIRE_ERROR(552, false, "系统错误，缺少过期时间！"),
    HTTP_URL_CONNECT_ERROR(551, false, "目标地址无法请求！"),

    // admin 管理系统 56x
    ADMIN_USERNAME_NULL_ERROR(561, false, "管理员登录名不能为空！"),
    ADMIN_USERNAME_EXIST_ERROR(562, false, "管理员账户名已存在！"),
    ADMIN_NAME_NULL_ERROR(563, false, "管理员负责人不能为空！"),
    ADMIN_PASSWORD_ERROR(564, false, "密码不能为空或者两次输入不一致！"),
    ADMIN_CREATE_ERROR(565, false, "添加管理员失败！"),
    ADMIN_PASSWORD_NULL_ERROR(566, false, "密码不能为空！"),
    ADMIN_LOGIN_ERROR(567, false, "用户名或密码错误！"),
    ADMIN_FACE_NULL_ERROR(568, false, "人脸信息不能为空！"),
    ADMIN_FACE_LOGIN_ERROR(569, false, "人脸识别失败，请重试！"),
    ADMIN_DELETE_ERROR(5691, false, "删除管理员失败！"),


    // 查询分页错误 57x
    PAGE_ERROR_LIMIT(570, false, "查询分页超过最大分页数！"),

    // 系统响应结果
    RESP_INSERT_ERROR(4101, false, "当前添加的响应结果类型已经存在，请勿重复添加！"),
    RESP_UPDATE_ERROR(4102, false, "当前修改的响应结果类型已经存在，修改失败！"),

    // 菜单模块
    MENU_DELETE_ERROR(5701, false, "当前顶级菜单下子级菜单不为空，无法删除顶级菜单！"),
    MENU_MOVE_UP_ERROR(5702, false, "当前菜单已经排在首位！"),
    MENU_MOVE_DOWN_ERROR(5703, false, "当前菜单已经排在末位！"),
    MENU_INSERT_ERROR(5704, false, "当前添加的菜单已经存在，请勿重复添加！"),
    MENU_UPDATE_ERROR(5705, false, "当前修改的菜单已经存在，修改失败！"),

    // 权限模块
    PERM_INSERT_ERROR(6701, false, "当前添加的权限已经存在，请勿重复添加！"),
    PERM_DELETE_ERROR(6702, false, "权限删除失败！"),
    PERM_UPDATE_ERROR(6703, false, "当前修改的权限已经存在，修改失败！"),
    PERM_UPDATE_NOT_FIND_ERROR(6704, false, "当前修改的权限不存在！"),
    PERM_MOVE_UP_ERROR(6705, false, "当前权限已经排在首位！"),
    PERM_MOVE_DOWN_ERROR(6706, false, "当前权限已经排在末位！"),

    // 角色模块
    ROLE_SELECT_ERROR(6801, false, "角色不存在！"),
    ROLE_INSERT_ERROR(6802, false, "当前添加的角色已经存在，请勿重复添加！"),


    // 人脸识别错误代码
    FACE_VERIFY_TYPE_ERROR(600, false, "人脸比对验证类型不正确！"),
    FACE_VERIFY_LOGIN_ERROR(601, false, "人脸登录失败！"),

    // 系统错误，未预期的错误
    SYSTEM_OPERATION_ERROR(556, false, "操作失败，请重试或联系管理员"),
    SYSTEM_RESPONSE_NO_INFO(557, false, ""),
    SYSTEM_ERROR_GLOBAL(558, false, "全局降级：系统繁忙，请稍后再试！"),
    SYSTEM_ERROR_FEIGN(559, false, "客户端Feign降级：系统繁忙，请稍后再试！"),
    SYSTEM_ERROR_ZUUL(560, false, "请求系统过于繁忙，请稍后再试！"),
    SYSTEM_PARAMS_SETTINGS_ERROR(5611, false, "参数设置不规范！"),
    SYSTEM_ERROR_BLACK_IP(5621, false, "请求过于频繁，请稍后重试！"),
    SYSTEM_SMS_FALLBACK_ERROR(5587, false, "短信业务繁忙，请稍后再试！"),
    SYS_DATA_ERROR(5588, false, "系统参数为空，请检查系统参数表sys_params！"),
    SYSTEM_ERROR_NOT_BLANK(5599, false, "系统错误，参数不能为空！"),

    DATA_DICT_EXIST_ERROR(5631, false, "数据字典已存在，不可重复添加或修改！"),
    DATA_DICT_DELETE_ERROR(5632, false, "删除数据字典失败！"),

    JWT_SIGNATURE_ERROR(5555, false, "用户校验失败，请重新登录！"),
    JWT_EXPIRE_ERROR(5556, false, "登录有效期已过，请重新登录！"),

    SENTINEL_BLOCK_FLOW_LIMIT_ERROR(5801, false, "系统访问繁忙，请稍后再试！"),

    // 支付错误相关代码
    PAYMENT_USER_INFO_ERROR(5901, false, "用户id或密码不正确！"),
    PAYMENT_ACCOUT_EXPIRE_ERROR(5902, false, "该账户授权访问日期已失效！"),
    PAYMENT_HEADERS_ERROR(5903, false, "请在header中携带支付中心所需的用户id以及密码！"),
    PAYMENT_ORDER_CREATE_ERROR(5904, false, "支付中心订单创建失败，请联系管理员！"),

    //用户错误相关
    USER_NOT_FIND(51001, false, "用户信息不存在！"),

    //订单错误相关
    ORDER_NOT_FIND(52001, false, "订单不存在！"),


    // ARGUMENT_NOT_VALID 是过滤器使用的错误提示，业务中不能使用！
    ARGUMENT_NOT_VALID(500, false, "参数校验不通过！");


    // 响应业务状态
    private Integer status;
    // 调用是否成功
    private Boolean success;
    // 响应消息，可以为成功或者失败的消息
    private String msg;

    ResponseStatusEnum(Integer status, Boolean success, String msg) {
        this.status = status;
        this.success = success;
        this.msg = msg;
    }

    public Integer status() {
        return status;
    }
    public Boolean success() {
        return success;
    }
    public String msg() {
        return msg;
    }


    public static ResponseStatusEnum get(String code){
        Iterator<ResponseStatusEnum> iterator = Arrays.stream(ResponseStatusEnum.values()).iterator();
        while (iterator.hasNext()){
            ResponseStatusEnum next = iterator.next();
            String name = next.name();
            if (name.equals(code)) {
                return next;
            }
        }
        return null;
    }

    public static List<ResponseStatusResult> getAllResponseStatus(){
        List<ResponseStatusResult> list = new ArrayList<>();
        Iterator<ResponseStatusEnum> iterator = Arrays.stream(ResponseStatusEnum.values()).iterator();
        while (iterator.hasNext()){
            ResponseStatusEnum next = iterator.next();
            ResponseStatusResult responseStatusResult = new ResponseStatusResult();
            responseStatusResult.code = next.name();
            responseStatusResult.status = next.status;
            responseStatusResult.success = next.success;
            responseStatusResult.msg = next.msg;
            list.add(responseStatusResult);
        }
        return list;
    }

    @Data
    public static class ResponseStatusResult {
        private String code;
        private Integer status;
        private Boolean success;
        private String msg;
    }

}
