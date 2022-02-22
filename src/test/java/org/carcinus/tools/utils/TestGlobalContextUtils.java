package org.carcinus.tools.utils;

import org.carcinus.tools.conf.Configuration;
import org.carcinus.tools.context.GlobalContext;

import java.util.HashMap;
import java.util.Map;

public class TestGlobalContextUtils {

    public static Map<String, String> getConf() {
        HashMap<String, String> conf = new HashMap<>();
//        put conf
        conf.put("auto.lottery.login.qr.path", "");

        return conf;
    }

    public static GlobalContext getGlobalContext() {
        Map<String, String> conf = getConf();
        return getGlobalContext(conf);
    }

    public static GlobalContext getGlobalContext(Map<String, String> conf) {
        GlobalContext.GlobalContextBuilder globalContextBuilder = GlobalContext.newBuilder()
                .setConf(new Configuration(conf));

        return globalContextBuilder.getOrCreate();
    }
}
