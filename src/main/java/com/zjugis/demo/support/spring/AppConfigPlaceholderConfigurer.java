package com.zjugis.demo.support.spring;

import com.zjugis.demo.support.config.ApplicationConfig;
import com.zjugis.demo.support.config.EncryptHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.PropertyPlaceholderHelper;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.springframework.util.SystemPropertyUtils.PLACEHOLDER_PREFIX;
import static org.springframework.util.SystemPropertyUtils.PLACEHOLDER_SUFFIX;

/**
 * @author <a href="mailto:unclemiki@163.com">UncleMiki</a>
 * @verison 1.0
 * @date 16/10/8
 */
public class AppConfigPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static final PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(PLACEHOLDER_PREFIX, PLACEHOLDER_SUFFIX, ":", true);

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        ApplicationConfig.setConfiguration(props);
        super.processProperties(beanFactoryToProcess, props);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Properties mergeProperties() throws IOException {
        Properties props = super.mergeProperties();
        for (Map.Entry entry : props.entrySet()) {
            String v = (String) entry.getValue();
            if (v != null && v.startsWith("!!")) {
                entry.setValue(EncryptHelper.decrypt(v));
            }
        }
        return props;
    }

    @Override
    protected String parseStringValue(String strVal, final Properties props, Set visitedPlaceholders) throws BeanDefinitionStoreException {
        return helper.replacePlaceholders(strVal, new PropertyPlaceholderHelper.PlaceholderResolver() {
            @Override
            public String resolvePlaceholder(String placeholderName) {
                return AppConfigPlaceholderConfigurer.this.resolvePlaceholder(placeholderName, props, SYSTEM_PROPERTIES_MODE_FALLBACK);
            }
        });
    }
}
