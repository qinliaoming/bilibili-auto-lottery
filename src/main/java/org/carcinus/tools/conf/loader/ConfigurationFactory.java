package org.carcinus.tools.conf.loader;

import org.carcinus.tools.conf.loader.impl.LocalConfigurationLoader;

public class ConfigurationFactory {
    public static ConfigurationLoader getInstance(){
        //todo impl other way
        return new LocalConfigurationLoader();
    }
}
