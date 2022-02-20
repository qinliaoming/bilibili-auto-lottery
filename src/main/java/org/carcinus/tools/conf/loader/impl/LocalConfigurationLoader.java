package org.carcinus.tools.conf.loader.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.carcinus.tools.conf.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class LocalConfigurationLoader implements ConfigurationLoader {

    private final String CONF_FILE = "config.properties";
    private final Logger logger = LoggerFactory.getLogger(getClass());


    private void loadConfProperties(Properties properties) {
        logger.info("loading conf.properties ");
        try (InputStream confInputStream = this.getClass().getClassLoader().getResourceAsStream(CONF_FILE)) {
            Preconditions.checkNotNull(confInputStream, CONF_FILE + "not exits !");
            properties.load(confInputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Override
    public Map<String, String> loadConfiguration() {

        Properties properties = new Properties();

        loadSystemProperties(properties);
        loadConfProperties(properties);

        return Maps.fromProperties(properties);
    }

    private void loadSystemProperties(Properties properties) {
        logger.info("loading system env configuration");
        properties.putAll(System.getProperties());
    }
}
