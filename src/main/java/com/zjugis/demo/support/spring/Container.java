package com.zjugis.demo.support.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author <a href="mailto:unclemiki@163.com">UncleMiki</a>
 * @verison 1.0
 * @date 16/9/28
 */
public class Container {

    protected final static Log logger = LogFactory.getLog(Container.class);

    private static BeanFactory factoryBean;

    public static void createApplicationContext(ServletContext ctx) {
        ContextLoader contextLoader = new ContextLoader();
        WebApplicationContext appContext = contextLoader.initWebApplicationContext(ctx);
        factoryBean = appContext;
        try {
//            String userCenterUrl = ApplicationConfig.getUserCenterUrl();
//            String storageCenterUrl = ApplicationConfig.getStorageCenterUrl();
//            String ssoUrl = ApplicationConfig.getSsoUrl();
//            ctx.setAttribute(ApplicationConfig.USER_CENTER_URL, userCenterUrl);
//            ctx.setAttribute(ApplicationConfig.STORAGE_CENTER_URL, storageCenterUrl);
//            ctx.setAttribute(ApplicationConfig.SSO_URL, ssoUrl);
        } catch (Exception e) {
            logger.error("spring context load error", e);
        }
    }

    public static Object getBean(String beanId) {
        if ((factoryBean != null) && (factoryBean.containsBean(beanId))) {
            return factoryBean.getBean(beanId);
        }
        throw new NoSuchBeanDefinitionException(beanId, " not found!");
    }
}
