package com.zjugis.demo.support.core;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author <a href="mailto:unclemiki@163.com">UncleMiki</a>
 * @verison 1.0
 * @date 16/5/24
 */
public abstract class BaseLogger {

    /**
     * base logger
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MessageSource message;

    /**
     * 获取message
     *
     * @param key
     * @param param
     * @return
     */
    protected String getMessage(String key, Object... param) {
        return message.getMessage(key, param, Locale.getDefault());
    }

    /**
     * info
     *
     * @param messageKey
     * @param param
     */
    protected void info(String messageKey, Object... param) {
        logger.info(getMessage(messageKey, param));
    }

    /**
     * error
     *
     * @param messageKey
     * @param param
     */
    protected void error(String messageKey, Object... param) {
        logger.error(getMessage(messageKey, param));
    }

    /**
     * warn
     *
     * @param messageKey
     * @param param
     */
    protected void warn(String messageKey, Object... param) {
        logger.warn(getMessage(messageKey, param));
    }

    /**
     * throw new runtime　exception
     *
     * @param messageKey
     * @param param
     * @throws RuntimeException
     */
    protected void throwException(String messageKey, Object... param) throws RuntimeException {
        throw new RuntimeException(getMessage(messageKey, param));
    }

    /**
     * is null
     *
     * @param value
     * @return
     */
    protected boolean isNull(Object value) {
        if (value == null) return true;
        if (value instanceof String && StringUtils.isBlank((String) value)) return true;
        return false;
    }


}
