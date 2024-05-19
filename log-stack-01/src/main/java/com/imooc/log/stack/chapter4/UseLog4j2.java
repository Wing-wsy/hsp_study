//package com.imooc.log.stack.chapter4;
//
//// 导入这两个是JCL的 和下面logger对应
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//// 导入这两个是log4j2的 和下面logger2对应
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
///**
// * <h1>使用 log4j2</h1>
// * */
//public class UseLog4j2 {
//
//    // 门面方式：推荐，更换其他日志框架不需要修改源代码
//    private static final Log logger = LogFactory.getLog(UseLog4j2.class);
//    // 直接使用实现方式：不推荐，如果更换其他日志框架需要修改源代码
//    private static final Logger logger2 = LogManager.getLogger(UseLog4j2.class);
//
//    // 这样做有什么好处呢 ? 好处就是，SubUseLog4j2是UseLog4j2子类，所以子类可以直接使用logger3
//    protected final Log logger3 = LogFactory.getLog(getClass());
//
//    /**
//     * <h2>log4j2 支持占位符, jcl 不支持</h2>
//     * */
//    public static void placeholder() {
//
//        logger2.info("use placeholder, not: [{}]", "abcde");
//    }
//
//    /**
//     * <h2>打印异常栈</h2>
//     * */
//    public static void logWithException() {
//
//        try {
//            System.out.println(Integer.parseInt("a"));
//        } catch (NumberFormatException ex) {
//            // ex要放在最后一个参数，因为前面的参数是放占位符的
//            logger.error("parse int has some error", ex);
//        }
//    }
//
//    public static void main(String[] args) {
//
//        // 最基本的打印方法
////        logger.fatal("use jcl + log4j2 to log fatal");
////        logger.error("use jcl + log4j2 to log error");
////        logger.info("use jcl + log4j2 to log info");
////        logger.debug("use jcl + log4j2 to log debug");
////        logger.trace("use jcl + log4j2 to log trace");
//
//        for (int i = 0; i < 10000; i++) {
//            logger2.fatal("use lo4j2 to log fatal");
//            logger2.error("use lo4j2 to log error");
//            logger2.warn("use lo4j2 to log warn");
//            logger2.info("use lo4j2 to log info");
//            logger2.debug("use lo4j2 to log debug");
//            logger2.trace("use lo4j2 to log trace");
//
//        }
//
//        // 测试占位符
////        placeholder();
////
////        logWithException();
//    }
//}
