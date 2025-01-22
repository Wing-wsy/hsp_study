package com.yz.common.constant;


/**
 * 缓存key值
 */
public class CacheKey {
    private CacheKey() {
    }

    /**
     * 业务key
     */
    public static final String MIS = "mis:";  // mis
    public static final String CST = "cst:";  // cst
    public static final String ODR = "odr:";  // odr
    public static final String PAY = "pay:";  // pay

    /**
     * 表名key（键名缩短处理，占用更少资源）
     */
    public static final String T_RESPONSE_ERROR_ENUMS = "response:";



    /**
     * 主键key
     */
    public static final String ID = "id:";

    public static final String CODE = "code:";



}
