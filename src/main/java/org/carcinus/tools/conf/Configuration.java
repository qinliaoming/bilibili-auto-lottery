package org.carcinus.tools.conf;

import org.carcinus.tools.conf.loader.ConfigurationFactory;
import org.carcinus.tools.conf.loader.ConfigurationLoader;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Configuration implements Serializable, Cloneable {
    private final Map<String, String> configuration;

    public String getConf(String key) {
        return configuration.get(key);
    }

    public void setConf(String key, String value) {
        configuration.put(key, value);
    }

    public Configuration() {
        this(true);
    }

    public Configuration(boolean isLoadLocal) {
        if (isLoadLocal) {
            ConfigurationLoader configurationLoader = ConfigurationFactory.getInstance();
            configuration = configurationLoader.loadConfiguration();
        } else {
            configuration = new ConcurrentHashMap<>();
        }
    }

    public Configuration(Map<String, String> conf) {
        this.configuration = conf;
    }

    @Override
    protected Configuration clone() {
        return new Configuration(configuration);
    }

    public Configuration(ConfigurationLoader configurationLoader) {
        configuration = configurationLoader.loadConfiguration();
    }
}
