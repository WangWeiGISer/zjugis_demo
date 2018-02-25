package com.zjugis.demo.support.config;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * App 配置文件读取
 * @author <a href="mailto:zhangbixi.cool@163.com">zhangbixi</a>
 * @version 1.0
 * @date: 2015/9/3
 */
public class ApplicationConfig implements InitializingBean {

    protected final static Log logger = LogFactory.getLog(ApplicationConfig.class);
    private static final PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}", ":", true);

    private static ConfigPlaceholderResolver resolver = new ConfigPlaceholderResolver();
    private String[] necessaryConfigs;
    private static Map appProperties = new HashMap();

    public static void setConfiguration(final Map properties) {
        resolver.setProps(appProperties = properties);
    }

    public void setNecessaryConfigs(String[] necessaryConfigs) {
        this.necessaryConfigs = necessaryConfigs;
    }

    /**
     * 获取配置文件或系统参数，系统环境中的参数值，如server.url
     *
     * @param key 参数名称.
     * @return 参数值.
     */
    public static String getProperty(final String key) {
        Assert.notNull(key, "Argument 'key' must not be null.");
        return resolver.resolvePlaceholder(key);
    }

    /**
     * 计算带占位符的表达式值，如：http://${server.url}/platform
     *
     * @param key 参数.
     * @return 参数值.
     */
    public static String getPlaceholderValue(final String key) {
        Assert.notNull(key, "Argument 'key' must not be null.");
        return helper.replacePlaceholders(key, resolver);
    }

    /**
     * 获取配置文件中的整数参数值.
     *
     * @param key 参数名称.
     * @return 整数参数值.
     */
    public static int getIntProperty(final String key) {
        return getIntProperty(key, 0);
    }

    /**
     * 获取配置文件中的整数参数.如果文件中没有该参数就返回defaultValue.
     *
     * @param key          参数名称.
     * @param defaultValue 参数默认值.
     * @return 整数参数值.
     */
    public static int getIntProperty(final String key, int defaultValue) {
        String property = getProperty(key);
        int value = defaultValue;

        try {
            value = Integer.parseInt(property);
        } catch (NumberFormatException e) {
            logger.warn(e.toString());
        }

        return value;
    }

    /**
     * 获取配置文件中的布尔参数值.
     *
     * @param key 参数名称.
     * @return 布尔参数值.
     */
    public static boolean getBooleanProperty(final String key) {
        return getBooleanProperty(key, false);
    }

    /**
     * 获取配置文件中的布尔参数.如果文件中没有该参数就返回defaultValue.
     *
     * @param key          参数名称.
     * @param defaultValue 参数默认值.
     * @return 布尔参数值.
     */
    public static boolean getBooleanProperty(final String key, final boolean defaultValue) {
        String value = getProperty(key);

        if (null != value) {
            return "true".equalsIgnoreCase(value)
                    || "on".equalsIgnoreCase(value)
                    || "yes".equalsIgnoreCase(value)
                    || "1".equalsIgnoreCase(value);
        } else {
            return defaultValue;
        }
    }

    public static Map getProperties() {
        return appProperties;
    }

    /**
     * 检查系统必须的配置参数是否设置
     *
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        // 检查某些关键的配置顶是否存在，不存在就报初始化错误
        final String[] keys = this.necessaryConfigs;
        if (keys == null)
            return;
        for (int i = 0, n = keys.length; i < n; i++) {
            String key = StringUtils.trimToEmpty(keys[i]);
            if (!appProperties.containsKey(key)) {
                throw new IllegalStateException("Can not find property \"" + key + "\" in configuration file.");
            }
        }
    }

    public static String getConfHome(String... name) {
        return getProperty(ApplicationConfigLoader.CONF_NAME + namesToPath(name));
    }

    public static String getDataHome(String... name) {
        return getProperty(ApplicationConfigLoader.DATA_NAME + namesToPath(name));
    }

    public static String getEgovHome() {
        return getProperty(ApplicationConfigLoader.HOME_NAME);
    }

    private static String namesToPath(String... name) {
        if (name != null && name.length > 0) {
            return "/" + StringUtils.join(name, "/");
        } else {
            return "";
        }
    }
}
