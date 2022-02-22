package org.carcinus.tools.context;

import lombok.Data;
import lombok.Setter;
import org.carcinus.tools.conf.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalContext {

    private void setConf(Configuration conf){
        this.conf = conf;
    }
    public void setConf(String key, String value) {
        this.conf.setConf(key, value);
    }

    private Configuration conf;

    public String getConf(String key) {
        return conf.getConf(key);
    }


    public static GlobalContextBuilder newBuilder() {
        return new GlobalContextBuilder();
    }

    public static class GlobalContextBuilder {

        private final Map<String, String> conf;
        private Configuration configuration;

        public GlobalContextBuilder() {
            conf = new ConcurrentHashMap<>();

        }


        public GlobalContextBuilder setConf(Configuration conf) {
            configuration = conf;
            return this;
        }

        public GlobalContextBuilder setConf(String key, String value) {
            conf.put(key, value);
            return this;
        }

        public GlobalContext getOrCreate() {
            GlobalContext globalContext = new GlobalContext();
            if (configuration == null) {
                configuration = new Configuration();
            }
            globalContext.setConf(configuration);

            if (!conf.isEmpty()) conf.forEach(configuration::setConf);
            return globalContext;
        }
    }
}
