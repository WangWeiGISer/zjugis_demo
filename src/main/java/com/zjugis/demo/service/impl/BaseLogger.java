package com.zjugis.demo.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author wangwei
 * @version V1.0, 2017/10/21
 */
public class BaseLogger {

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
     * @param value
     * @return
     */
    protected boolean isNull(Object value) {
        if (value == null) return true;
        if (value instanceof String) return StringUtils.isBlank((String) value);
        return false;
    }

    /**
     * is not null
     *
     * @param value
     * @return
     */
    protected boolean isNotNull(Object value) {
        return !isNull(value);
    }
}
