package org.carcinus.tools.context;

import org.apache.http.Header;
import org.carcinus.tools.conf.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalContext {

    private Configuration conf;
    private boolean readyState;
    private List<Header> cookieHeaders;
    private GlobalContext() {
    }

    public boolean isReadyStatus() {
        return readyState;
    }

    public void setReadyStatus(boolean readyState) {
        this.readyState = readyState;
    }

    private void setConf(Configuration conf){
        this.conf = conf;
    }

    public void setConf(String key, String value) {
        this.conf.setConf(key, value);
    }

    public String getConf(String key) {
        return conf.getConf(key);
    }
    public String getConf(String key, String defaultValue) {
        String value = this.conf.getConf(key);
        return value == null ? defaultValue : value;
    }

    public List<Header> getCookieHeaders() {
        if (cookieHeaders == null) return new ArrayList<>();
        return cookieHeaders;
    }

    public void setCookieHeaders(List<Header> cookieHeaders) {
        this.cookieHeaders = cookieHeaders;
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
