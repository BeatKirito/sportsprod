package com.csb.sports.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 规范化日志打印工具
 * @author 陈少波
 * @version $Id: LoggerUtil, v0.1 2017年05月01日 21:48 Exp $
 */
public class LoggerUtil {

    /** 错误日志 */
    private static final Logger ERROR_LOGGER = LoggerFactory.getLogger("ERROR-LOGGER");

    /** 运动数据平台业务日志 */
    public static final Logger SPORTSPROD_LOGGER = LoggerFactory.getLogger("SPORTSPROD-CORE-LOGGER");

    /**
     * 禁用构造函数
     */
    protected LoggerUtil() {

    }

    /**
     * 生成<font color="blue">调试</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger
     * @param obj
     */
    public static void debug(Logger logger,Object... obj) {
        if(logger.isDebugEnabled()) {
            logger.debug(getLogString(obj));
        }
    }

    /**
     * 生成<font color="blue">通知</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger
     * @param obj
     */
    public static void info(Logger logger, Object... obj) {
        if (logger.isInfoEnabled()) {
            logger.info(getLogString(obj));
        }
    }

    /**
     * 如果满足断言的条件则打印警告级别日志
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger
     * @param obj
     */
    public static void warn(boolean expression, Logger logger, Object... obj) {
        if (expression && logger.isWarnEnabled()) {
            logger.warn(getLogString(obj));
        }
    }

    /**
     * 生成<font color="brown">警告</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger
     * @param obj
     */
    public static void warn(Logger logger, Object... obj) {
        if (logger.isWarnEnabled()) {
            logger.warn(getLogString(obj));
        }
    }

    /**
     * 生成<font color="brown">警告</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param logger
     * @param obj
     */
    public static void warn(Logger logger, Throwable e, Object... obj) {
        if (logger.isWarnEnabled()) {
            logger.warn(getLogString(obj), e);
        }
    }

    /**
     * 生成<font color="brown">错误</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     *
     * @param e
     * @param obj
     */
    public static void error(Throwable e, Object... obj) {
        ERROR_LOGGER.error(getLogString(obj), e);
    }

    /**
     * 生成输出到日志的字符串
     *
     * @param obj 任意个要输出到日志的参数
     * @return
     */
    public static String getLogString(Object... obj) {
        StringBuilder log = new StringBuilder();
        log.append("[").append(Thread.currentThread().getId()).append("]");
        for (Object o : obj) {
            log.append(o);
        }
        return log.toString();
    }

}
