package org.carcinus.tools.utils;

import org.carcinus.tools.conf.Configuration;
import org.carcinus.tools.context.GlobalContext;

import java.util.Map;

public class TestGlobalContextUtils {

    public static GlobalContext getGlobalContext() {
        GlobalContext globalContext = GlobalContext.newBuilder()
                .setConf(new Configuration(false))
                .getOrCreate();
        return globalContext;
    }

    public static GlobalContext getGlobalContext(Map<String, String> conf) {
        GlobalContext.GlobalContextBuilder globalContextBuilder = GlobalContext.newBuilder()
                .setConf(new Configuration(false));

        conf.forEach(globalContextBuilder::setConf);

        return globalContextBuilder.getOrCreate();
    }
}
