package com.imooc.log.stack.chapter4;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <h1>Logback 的使用</h1>
 * */
public class UseLogback {

    private static final Logger logger = LoggerFactory.getLogger(UseLogback.class);

    /**
     * <h2>支持占位符</h2>
     * */
    private static void log() {
        logger.info("this is slf4j + logback: [{}]", UseLogback.class.getName());
    }

    private static void levelLog() {

        for (int i = 0; i < 100000; i++) {
            logger.trace("aslf4j + logback: [{}]", "trace");
            logger.debug("aslf4j + logback: [{}]", "debug");
            logger.info("slf4j + logback: [{}]", "info");
            logger.error("aslf4j + logback: [{}]", "error");
            logger.warn("slf4j + logback: [{}]", "warn");
        }

    }

    /**
     * <h2>打印 logback 的内部状态</h2>
     * */
    private static void printLogbackStatus() {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(loggerContext);
    }

    public static void main(String[] args) {
//        log();

//        levelLog();

//        printLogbackStatus();

        // -2147483648
//        System.out.println(Integer.MIN_VALUE);
//        // 2147483647
//        System.out.println(Integer.MAX_VALUE);
//        // int
//        System.out.println(Integer.TYPE);
//        System.out.println(Integer.digits);

//        Integer i1 = new Integer(1);
//        String str = Integer.toString(8);
//        System.out.println(str);
//        inx

//        for (int i=0; ; i++)
//        for (; ;)/("1");

        List<Integer> list = new ArrayList<>();

    }
}
