package com.zjugis.demo.support.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @ClassName: DataBaseContextHolder
 * @Description: ${TODO}(用一句话描述该文件)
 * @Author: Unclemiki <a href='mailto:unclemiki@163.com'>UncleMiki</a>
 * @Version V1.0
 * Date           17/2/16  下午4:37
 **/
public class DataBaseContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DataBaseContextHolder.class);

    private static final ThreadLocal contextHolder = new ThreadLocal();

    public static void setDataBaseType(String type) {
        Assert.notNull(type, "类型不可为空");
        contextHolder.set(type);
    }

    public static String getDataBaseType() {
        return (String) contextHolder.get();
    }

    public static void clearDataBaseType() {
        contextHolder.remove();
    }
}
