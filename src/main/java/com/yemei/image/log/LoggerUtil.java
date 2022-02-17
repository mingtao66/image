package com.yemei.image.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * log4j2正常log包装类，用于定义log格式
 *
 * @author JiangMingtao
 */
public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class.getName());

    /**
     * Logs a message with SEVERE level.
     *
     * @param classname Class logging the message.
     * @param msg       Message to be logged.
     */
    @SuppressWarnings("rawtypes")
    public static void error(Class classname, String msg) {
        StringBuffer buf = new StringBuffer(200);
        buf.append("(" + classname.getName() + ") " + msg);
        logMessage(buf.toString(), Level.ERROR);
    }

    /**
     * Logs a message with SEVERE level.
     *
     * @param classname Class logging the message.
     * @param msg       Message to be logged.
     * @param e         Exception to be printed
     */
    @SuppressWarnings("rawtypes")
    public static void error(Class classname, String msg, Throwable e) {
        StringBuffer buf = new StringBuffer(200);
        buf.append("(" + classname.getName() + ") " + msg);
        logMessage(buf.toString(), e, Level.ERROR);
    }

    /**
     * Logs a message with SEVERE level.
     *
     * @param classname Class logging the message.
     * @param msg       Message to be logged.
     */
    @SuppressWarnings("rawtypes")
    public static void warn(Class classname, String msg) {
        logMessage(msg, Level.WARN);
    }

    /**
     * Logs a message with SEVERE level.
     *
     * @param classname Class logging the message.
     * @param msg       Message to be logged.
     * @param e         Exception to be printed
     */
    @SuppressWarnings("rawtypes")
    public static void warn(Class classname, String msg, Throwable e) {
        logMessage(msg, e, Level.WARN);
    }

    /**
     * Logs a message with INFO level.
     *
     * @param classname Class logging the message. Ignored, not logged.
     * @param msg       Message to be logged.
     */
    @SuppressWarnings("rawtypes")
    public static void info(Class classname, String msg) {
        if (logger.isInfoEnabled() || logger.isDebugEnabled()) {
            StringBuffer buf = new StringBuffer(200);
            buf.append("(" + classname.getName() + ") " + msg);
            logMessage(buf.toString(), Level.INFO);
        }
    }

    /**
     * Logs a message with INFO level.
     *
     * @param classname Class logging the message. Ignored, not logged.
     * @param msg       Message to be logged.
     * @param e         Exception to be printed
     */
    @SuppressWarnings("rawtypes")
    public static void info(Class classname, String msg, Throwable e) {
        if (logger.isInfoEnabled() || logger.isDebugEnabled()) {
            StringBuffer buf = new StringBuffer(200);
            buf.append("(" + classname.getName() + ") " + msg);
            logMessage(buf.toString(), e, Level.INFO);
        }
    }

    /**
     * Logs a message with FINE level for not debug and WARNING level for debug
     *
     * @param classname Class logging the message. Ignored, not logged.
     * @param msg       Message to be logged.
     */
    @SuppressWarnings("rawtypes")
    public static void debug(Class classname, String msg) {
        StringBuffer buf = new StringBuffer(200);
        buf.append("(" + classname.getName() + ") " + msg);

        if (logger.isDebugEnabled()) {
            logMessage(buf.toString(), Level.DEBUG);
        }

    }

    @SuppressWarnings("rawtypes")
    private static void logMessage(String msg, Level level) {
        logger.log(level, msg);
    }

    @SuppressWarnings("rawtypes")
    private static void logMessage(String msg, Throwable e, Level level) {
        logger.log(level, msg, e);
    }


    public static boolean isInfoEnabled() {
        return logger.isInfoEnabled() || logger.isDebugEnabled();
    }


}
