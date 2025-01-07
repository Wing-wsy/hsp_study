package com.example.hxds.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * @author wing
 * @create 2025/1/5
 */
public class SnowUtil {
    private static long dataCenterId = 1;  //数据中心
    private static long workerId = 1;     //机器标识

    public static long nextId() {
        // hutool
//        return IdUtil.getSnowflake(workerId, dataCenterId).nextId();

        // 自定义
        return new SnowflakeIdWorker(dataCenterId, workerId).createId();
    }

    public static String nextIdStr() {
        // hutool
//        return IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();

        // 自定义
        return Long.toString(new SnowflakeIdWorker(dataCenterId, workerId).createId());

    }
}
