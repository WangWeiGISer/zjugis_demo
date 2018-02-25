package com.zjugis.demo.support.config;


import java.util.Collections;
import java.util.Map;

/**
 * @author <a href="mailto:zhangbixi.cool@163.com">zhangbixi</a>
 * @version 1.0
 * @date: 2015/3/20
 */
public class ConfigPlaceholderResolver implements PropertyPlaceholderHelper.PlaceholderResolver {
    private Map props;

    public ConfigPlaceholderResolver(Map props) {
        this.props = props;
    }

    public ConfigPlaceholderResolver() {
        props = Collections.EMPTY_MAP;
    }

    public void setProps(Map props) {
        this.props = props;
    }

    public String resolvePlaceholder(String placeholderName) {
        String propVal = (String) props.get(placeholderName);
        if (propVal == null) {
            propVal = System.getProperty(placeholderName);
            if (propVal == null) {
                propVal = System.getenv(placeholderName);
            }
        }
        return propVal;
    }
}
