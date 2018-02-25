package com.zjugis.demo.support.config;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="mailto:unclemiki@163.com">UncleMiki</a>
 * @verison 1.0
 * @date 16/9/28
 */
public class ApplicationConfigLoader extends PropertiesLoaderSupport {

    protected final Log logger = LogFactory.getLog(ApplicationConfigLoader.class);
    private static final PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}", ":", true);
    public static final String HOME_NAME = "zjugis.home";
    public static final String CONF_NAME = "zjugis.conf";
    public static final String DATA_NAME = "zjugis.data";
    public static final String PROPERTY_FILE_NAME = "zjugis.properties";
    public static final String DEFAULT_ACTIVE_CONF = "default";
    public static final String EGOV_HOME_FOLDER = "zjugis-home";

    public static void load(final String... paths) {
        ApplicationConfigLoader cl = new ApplicationConfigLoader();
        cl.loadConfig(paths);
    }

    public void loadConfig(final String... paths) {
        String configHome = getConfigProperty(HOME_NAME);
        if (configHome != null) {
            logger.info("zjugis home has been set to [" + configHome + "],skip");
            return;
        }
        File root = determineRootDir(paths);
        File home;
        if (root != null) {
            logger.info("Use zjugis-home dir:[" + root + "]");
            File activeFile = new File(root, "active.conf");
            String active = DEFAULT_ACTIVE_CONF;
            try {
                active = FileUtils.readFileToString(activeFile);
            } catch (IOException e) {
                logger.info("Read active profile from [" + activeFile.getAbsolutePath() + "] fail,use default");
            }
            home = new File(root, active);
            if (!home.exists()) {
                home = new File(root, DEFAULT_ACTIVE_CONF);
                if (!home.exists()) {
                    logger.error("zjugis active conf [" + home.getAbsoluteFile() + "] not exsit");
                }
            }
            logger.info("Use Custom config home dir:[" + home + "]");
        } else {
            home = new File("/opt/zjugis/config/default");
            if (home.exists()) {
                logger.warn("Use compact config home dir:[" + home.getAbsolutePath() + "],please convert to new format");
                setConfigProperty(HOME_NAME, new File("/opt/zjugis"));
                setConfigProperty(CONF_NAME, home);
                setConfigProperty(DATA_NAME, new File("/opt/zjugis/data"));
                loadProperties(new File(home, PROPERTY_FILE_NAME));
                return;
            }
            URL url = this.getClass().getResource("/META-INF/conf/" + PROPERTY_FILE_NAME);
            if (url != null) {
                try {
                    home = new File(url.toURI()).getParentFile().getParentFile();
                    logger.info("Custom config home not found,Use classpath config home dir [" + home.getAbsolutePath() + "]");
                } catch (URISyntaxException ignored) {
                    return;
                }
            } else {
                logger.error("Load config error,config not found");
                return;
            }
        }
        String homePath = home.getAbsolutePath();
        String confPath = homePath + File.separator + "conf";
        setConfigProperty(HOME_NAME, home);
        setConfigProperty(CONF_NAME, new File(confPath));
        if (getConfigProperty(DATA_NAME) == null)
            setConfigProperty(DATA_NAME, new File(homePath + File.separator + "data"));
        loadProperties(new File(confPath, PROPERTY_FILE_NAME));
    }

    private void loadProperties(File file) {
        setLocation(new FileSystemResource(file));
        try {
            Properties props = mergeProperties();
            ConfigPlaceholderResolver resolver = new ConfigPlaceholderResolver(props);
            Enumeration names = props.propertyNames();
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = props.getProperty(name);
                value = helper.replacePlaceholders(value, resolver);
                setConfigProperty(name, helper.replacePlaceholders(value, resolver));
            }
        } catch (IOException e) {
            logger.error("error to load props file:[" + file.getAbsolutePath() + "]", e);
        }
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

    protected void setConfigProperty(String key, File file) {
        setConfigProperty(key, file.toURI().toString());
        setConfigProperty(key + ".path", file.getAbsolutePath());
        logger.info("Use " + key + " dir:[" + file.getAbsolutePath() + "]");
    }

    protected void setConfigProperty(String key, String value) {
        logger.debug("Set config property [" + key + "=" + value + "]");
        System.setProperty(key, value);
    }

    protected String getConfigProperty(String key) {
        return System.getProperty(key);
    }

    private File determineRootDir(final String... paths) {
        File root;
        if (paths != null) {
            for (String path : paths) {
                root = new File(path);
                if (root.exists())
                    return root;
            }
        }
        for (String path : new String[]{
                System.getProperty("ZJUGIS_HOME"),
                System.getenv("ZJUGIS_HOME")
        }) {
            if (path != null) {
                root = new File(path);
                if (root.exists())
                    return root;
            }
        }
        for (String path : new String[]{
                System.getProperty("catalina.base"),
                System.getProperty("catalina.home"),
                System.getProperty("user.home")
        }) {
            if (path != null) {
                root = new File(path, EGOV_HOME_FOLDER);
                if (root.exists())
                    return root;
            }
        }
        return null;
    }

}
