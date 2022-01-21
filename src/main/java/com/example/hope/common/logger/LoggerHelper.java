package com.example.hope.common.logger;

/**
 * @description: 日志工具类
 * @author: DHY
 * @created: 2021/02/19 00:58
 */
public class LoggerHelper {

    private static final String logTemplate = "%s -> %s -> res -> %d";

    public static String logger(Object object, int res) {
        return String.format(logTemplate, Thread.currentThread().getStackTrace()[2].getMethodName(), object.toString(), res);
    }
}
