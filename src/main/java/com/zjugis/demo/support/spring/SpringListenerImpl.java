package com.zjugis.demo.support.spring;

import com.zjugis.demo.support.config.ApplicationConfigLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * @author <a href="mailto:unclemiki@163.com">UncleMiki</a>
 * @verison 1.0
 * @date 16/9/28
 */
public class SpringListenerImpl extends ContextLoaderListener {

    protected final static Log logger = LogFactory.getLog(SpringListenerImpl.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ApplicationConfigLoader.load(new String[0]);
            Container.createApplicationContext(servletContextEvent.getServletContext());
            logger.info("Spring applicationContext create success");
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.error("Spring applicationContext create fail", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {


    }
}
