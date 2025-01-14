package com.yz.common.util.snow;

/**
 * 雪花算法工具类
 */
public class SnowUtil {
    private static long dataCenterId = 1;  //数据中心
    private static long workerId = 1;     //机器标识

    /**
     * 生成雪花值（long类型）
     * @return
     */
    public static long nextId() {
        return new SnowflakeIdWorker(dataCenterId, workerId).createId();
    }


    /**
     * 生成雪花值（String类型）
     * @return
     */
    public static String nextIdStr() {
        return Long.toString(new SnowflakeIdWorker(dataCenterId, workerId).createId());

    }
}
